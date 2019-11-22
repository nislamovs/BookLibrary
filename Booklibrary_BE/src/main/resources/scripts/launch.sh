#!/usr/bin/env bash

cd ./scripts ;

GREEN_BOLD_TEXT="\e[1m \e[32m"
DEFAULT_TEXT="\e[39m \e[0m"

msg() {
    local text=$1
    local timestamp=$(date +"%d-%m-%y %T.%3N")
    echo -e "$GREEN_BOLD_TEXT$timestamp  $text$DEFAULT_TEXT"
}
#Generating book data for DB import:
msg "Generating books nomenclature [book details, status, images]... "
#bash -x "./generateBook_BookData_BookImage.sh" 2>/dev/null ;
msg "Done!!!"

#Generating debts list...
msg "Generating debts list ... "
bash -x "./generateDebts.sh" 2>/dev/null ;
msg "Done!!!"

#Generating history list...
msg "Generating history list ... "
bash -x "./generateHistory.sh" 2>/dev/null ;
msg "Done!!!"

#Generating payments list...
msg "Generating payments list ... "
bash -x "./generatePayments.sh" 2>/dev/null ;
msg "Done!!!"

#Generating penalty plan list...
msg "Generating penalty plan list ... "
#bash -x "./generatePenaltyPlans.sh" 2>/dev/null ;
msg "Done!!!"

#Generating visitors list...
msg "Generating visitors list ... "
#bash -x "./generateVisitorData.sh" 2>/dev/null ;
msg "Done!!!"
