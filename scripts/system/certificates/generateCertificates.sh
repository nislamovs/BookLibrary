#!/usr/bin/env bash

. tableLib.sh

#Initial data
CN=localhost
OU=Oracul
O=Oracul
L=Riga
ST=Latvia
C=LV

KEYSIZE=2048
KEYALG=RSA

DNAME="CN=$CN, OU=$OU, O=$O, L=$L, ST=$ST, C=$C"
TABLE_HEADER="Module name [alias], Path, CN, OU, O, L, ST, C, Keysize, KeyAlg, Keystore, Storepass"
MODULES=()
PROJECT_ROOT=""
CERT_SUBDIR=src/main/resources/certificates
CERT_EXT="p12"
TABLE_DATA=$TABLE_HEADER
TABLE_ROW="";

function prepare()
{
    #Go to project root folder
    echo -e ""
    echo -e "Certificates regeneration"
    echo -e "Locating project root..."
    cd ../../.. ;
    PROJECT_ROOT=`pwd`
}

function retrieveFolderList()
{
    #Retrieve folder list
    echo -e "Retrieve module list..."
    MODULE_LIST=$(ls -d */ | egrep -v 'Scripts|stuff|gradle|logs|UsersManager-FE' | sed 's/.$//')
    n=0
    for item in ${MODULE_LIST}
    do
      MODULES[ $n ]=$item
      (( n++ ))
    done
}

function cleanupOldCertificates()
{
    #Cleanup old certificates
    echo -e "Deleting old certificates..."
    cd $PROJECT_ROOT
    find ./*/"$CERT_SUBDIR"/*."$CERT_EXT" -exec rm -rf {} +
}

function genPass()
{
    #Generate new password...
    date +%s%N | cut -b1-13 | md5sum | gawk -F\  '{ print $1 }'
}

function updatePass()
{
    local newPass=$1
    local settingsFilePath="$2../application.yml"
    sed -i "/    key-store-password: /c\    key-store-password: $newPass" $settingsFilePath
    sed -i "/    key-password: /c\    key-password: $newPass" $settingsFilePath
}

function generateNewCertificates()
{
    #Generate new certificates....
    echo -e "Generating new certificates..."

    for module in "${MODULES[@]}"
    do
        MODULE_PATH="./$module/$CERT_SUBDIR/"

        PASSWD=`genPass`

        updatePass $PASSWD $MODULE_PATH;

        ALIAS=`echo "$module" | tr '[:upper:]' '[:lower:]'`

        TABLE_DATA+="\n${ALIAS}, "", ${CN}, ${OU}, ${O}, ${L}, ${ST}, ${C}, ${KEYSIZE}, ${KEYALG}, ${module}.${CERT_EXT}, ${PASSWD}"

        cd $MODULE_PATH



        keytool -dname "$DNAME" \
            -keysize "$KEYSIZE" \
            -genkey -alias $ALIAS \
            -keyalg "$KEYALG" \
            -keystore ${module}.${CERT_EXT} \
            -storetype PKCS12 \
            -validity 3650 \
            -keypass ${PASSWD} \
            -storepass ${PASSWD}

        cd $PROJECT_ROOT ;

    done
}

#Here comes the fun...
prepare ;
retrieveFolderList ;
cleanupOldCertificates ;
generateNewCertificates ;
#
#
printTable ',' "$TABLE_DATA" > certificates.txt && less certificates.txt