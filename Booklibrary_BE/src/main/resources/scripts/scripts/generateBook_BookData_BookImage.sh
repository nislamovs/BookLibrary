#!/usr/bin/env bash

. ./constants.sh
. ./apiKeys.sh

cd .. ;
APIKEY=$BOOK_KEY
CATALOGS_ISBNS=()

OUTPUT_LOG="LOG5.txt"

mkdir -p $BOOK_DATA_OUTPUT_FILENAME_PREFIX
mkdir -p $BOOK_OUTPUT_FILENAME_PREFIX
mkdir -p $BOOK_IMAGE_OUTPUT_FILENAME_PREFIX/photos

gen_pub_year_interval_substring() {
    local PUB_YEAR_INTERVAL=$1
    local PUB_YEAR_START=$(echo "$PUB_YEAR_INTERVAL" | gawk -F"-" {'print $1'})
    local PUB_YEAR_END=$(echo "$PUB_YEAR_INTERVAL" | gawk -F"-" {'print $2'})
    local INTERVAL_SUBSTRING=""

    for (( PUB_YEAR=$PUB_YEAR_START; PUB_YEAR<=$PUB_YEAR_END; PUB_YEAR++ ))
    do
        INTERVAL_SUBSTRING="$INTERVAL_SUBSTRING&publicationyear=$PUB_YEAR"
    done

    echo "${INTERVAL_SUBSTRING}"
}

retrieve_ISBN_numbers() {

    #Preparation of all variables
    local PUBLICATION_YEAR_INTERVAL=$1
    local PRODUCT_TYPE=$2
    local PAGE_NUMBER="1"
    local PAGE_SIZE="2000"
    local CATALOG_PARAM=$3

    local CATALOG_NO=$(echo "$CATALOG_PARAM" | gawk -F"-" {'print $2'})
    local CATALOG_NAME=$(echo "$CATALOG_PARAM" | gawk -F"-" {'print $1'})

    local FILENAME="ISBN_"$CATALOG_NAME".txt"

    #Constructing the link
    local PUB_YEAR_SUBSTRING=$(gen_pub_year_interval_substring $PUBLICATION_YEAR_INTERVAL)

    local LINK="https://www.elsevier.com/catalog?producttype=$PRODUCT_TYPE&cat0=$CATALOG_NO&cat1=&cat2=&series=&q=&search=1&imprintname=&\
categoryrestriction=&page=$PAGE_NUMBER&size=$PAGE_SIZE&sort=relevance$PUB_YEAR_SUBSTRING"

#    echo "Retrieving $CATALOG_NAME catalog content... "

    #Making request to the site
    local RESPONSE=$(curl --max-filesize 20000000 $LINK)

    #Filtering out related book count
    local BOOK_COUNT=`echo "$RESPONSE" | egrep -C 5 "Showing" | egrep "Products" | gawk -F">" {'print $2'} | gawk -F"<" {'print $1'} `

    #Resetting request path vars [to retrieve all books making only one request] and launching request again
    PAGE_SIZE=$BOOK_COUNT
    RESPONSE=$(curl $LINK)

    #Retrieving ISBN list from response
    mkdir -p ISBN
    echo "$RESPONSE" | egrep ">eBook: </strong>978-" | gawk -F"</strong>" {'print $2'} | gawk -F"</p>" {'print $1'} | tr -d '-' #> ISBN/$FILENAME
}

preprocess_response() {
    local RESP=$1
    local STATUS=$(echo "$RESP" | grep "403")
#    echo ">>>>    $STATUS"
    if [[ ! -z "$STATUS" ]]; then
        STATUS="LIMIT EXCEEDED"
    else
        STATUS="OPERATIONAL"
        local BOOK_PRESENCE=$(echo "$RESP" | jq '.totalItems')
        if [[ "$BOOK_PRESENCE" -eq 1 ]]; then
            STATUS="BOOK FOUND "${STATUS}
        elif [[ "$BOOK_PRESENCE" -eq 0 ]]; then
            STATUS="BOOK NOT FOUND "${STATUS}
        fi
    fi

    echo "$STATUS"
}

#Retrieving all ISBN's
for ((idx=0; idx<${#CATALOGS_IDS[@]}; idx++)); do
    echo "Retrieving ISBNs: catNo: $idx - [${CATALOGS_IDS[idx]}] ..."
    CATALOGS_ISBNS[${idx}]=$(retrieve_ISBN_numbers ${PUBLICATION_YEAR_INTERVAL} ${PRODUCT_TYPE} ${CATALOGS_IDS[idx]})
    if (( $idx==0)); then
        break;
    fi
    echo "Done."
done

echo "---------------------------------------------------------------------------------------------------------------"  >> $OUTPUT_LOG
echo "--------------------------------------------NEW SESSION--------------------------------------------------------"  >> $OUTPUT_LOG
echo "---------------------------------------------------------------------------------------------------------------"  >> $OUTPUT_LOG
echo ""                                                                                                                 >> $OUTPUT_LOG

#Iterate over packs of ISBN's [grouped by subjects] in array
echo "Iterating over isbn packs..."
for ((idx=0; idx<${#CATALOGS_ISBNS[@]}; idx++)); do

    CATEGORY=$(echo "${CATALOGS_IDS[idx]}" | awk -F"-" {'print $1'})
    FNAME=$BOOK_DATA_OUTPUT_FILENAME_PREFIX""$CATEGORY""$OUTPUT_FILENAME_POSTFIX

    echo "Catalog index : $idx; catalog name : $CATEGORY"

    while read -r ISBN; do

    #    LINK="https://www.googleapis.com/books/v1/volumes?&q=isbn:"$ISBN"&callback=ProcessGBSBookInfo&key="$APIKEY
        LINK="https://www.googleapis.com/books/v1/volumes?&q=isbn:"$ISBN"&callback=ProcessGBSBookInfo"

        RESPONSE=$(curl $LINK -H "Content-type: application/json" 2>/dev/null \
        | grep -v "// API callback" | grep -v "ProcessGBSBookInfo" \
        | sed '$ d' | sed '1s/^/{\n/')

        STATUS=$(preprocess_response "$RESPONSE")

        echo "---------------------------------------------------------------------------------------------------------------"  >> $OUTPUT_LOG
        echo "$RESPONSE"                                                                                                        >> $OUTPUT_LOG

        echo "$BOOK_ID $CATEGORY $ISBN   ---  $STATUS"

        if [[ "$STATUS" == *"BOOK FOUND"* ]]; then
echo "we are here"
            if [[ ! -f "$FNAME" ]]; then
               echo "books:" > ${FNAME}
            fi

            BOOK_ID=$((BOOK_ID+1))
            BOOK_TITLE=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.title' )

    #        BOOK_ISBN=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.industryIdentifiers[0].identifier' )
            BOOK_ISBN=\"$ISBN\"

            BOOK_PUBLISHER=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.publisher' )
            BOOK_PUBLISHEDDATE=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.publishedDate' | tr -d '"["')

    #        BOOK_AUTHORS=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.authors' | tr -d '[' | tr -d ']' | tr -d '\n')
            BOOK_AUTHORS=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.authors'  | tr -d '\n')

            BOOK_CATEGORIES=\"$CATEGORY\"
            BOOK_SEARCHINFO=$(echo "$RESPONSE"  | jq '.items[0] .searchInfo.textSnippet' )
            BOOK_ANNOTATION=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.description' )
            BOOK_PAGES=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.pageCount' )
            BOOK_LANGUAGE=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.language' | awk '{ print toupper($0) }' )
            BOOK_TOTALCOUNT=$(shuf -i1-25 -n1)
            BOOK_AVAILABLECOUNT=$(shuf -i1-$BOOK_TOTALCOUNT -n1)
            BOOK_POPULARITYRATE=$(shuf -i1-5 -n1)"."$(shuf -i1-9 -n1)
            BOOK_PRICE="\"EUR "$(echo "$RESPONSE"  | jq '.items[0] .saleInfo.retailPrice.amount')"\""

            BOOK_IMAGELINK=$(echo "$RESPONSE"  | jq '.items[0] .volumeInfo.imageLinks.thumbnail' | tr -d '"' )

    #       Book_data saving to file...
            echo "  - bookId: $BOOK_ID" >> $FNAME
            echo "    bookTitle: $BOOK_TITLE" >> $FNAME
            echo "    isbn: $BOOK_ISBN" >> $FNAME
            echo "    publisher: $BOOK_PUBLISHER" >> $FNAME
            echo "    publishedDate: $BOOK_PUBLISHEDDATE" >> $FNAME
            echo "    authors: $BOOK_AUTHORS" >> $FNAME
            echo "    categories: $BOOK_CATEGORIES" >> $FNAME
            echo "    searchInfo: $BOOK_SEARCHINFO" >> $FNAME
            echo "    annotation: $BOOK_ANNOTATION" >> $FNAME
            echo "    pages: $BOOK_PAGES" >> $FNAME
            echo "    language: $BOOK_LANGUAGE" >> $FNAME
            echo "    totalCount: $BOOK_TOTALCOUNT" >> $FNAME
            echo "    availableCount: $BOOK_AVAILABLECOUNT" >> $FNAME
            echo "    popularityRate: $BOOK_POPULARITYRATE" >> $FNAME
            echo "    internalPrice: $BOOK_PRICE" >> $FNAME
            echo "    bookPhoto: {}" >> $FNAME

    #       Book saving to file...
            if [[ ! -f "$BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX" ]]; then
                echo "bookstorage:" > $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX
            fi

            for (( Z=1; Z<=$BOOK_TOTALCOUNT; Z++ ))
            do
                BOOKNUMBER=$((BOOKNUMBER+1))
                echo "  - bookNumber: $BOOKNUMBER" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX
                echo "    bookId: $BOOK_ID" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX
                echo "    isbn: $BOOK_ISBN" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX

                if [[ "$Z" -le "$DEBT_COUNT" ]]; then
                    echo "    status: LOST" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX
                else
                    echo "    status: ON_SHELF" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX
                fi

                if [[ $Z<=2500 ]]; then
                    VISITOR_ID=${Z}
                else
                    VISITOR_ID=''
                fi

                echo "    visitorId: $VISITOR_ID" >> $BOOK_OUTPUT_FILENAME_PREFIX"ALL_BOOKS"$OUTPUT_FILENAME_POSTFIX

            done

    #       Saving book image...
            if [[ ! -f "$BOOK_IMAGE_OUTPUT_FILENAME_PREFIX"ALL_BOOK_IMAGES"$OUTPUT_FILENAME_POSTFIX" ]]; then
                echo "bookImageStorage:" > $BOOK_IMAGE_OUTPUT_FILENAME_PREFIX"ALL_BOOK_IMAGES"$OUTPUT_FILENAME_POSTFIX
            fi

    #       BOOK_IMAGE=$(curl -s "$LINK"  2>/dev/null | base64)
            IMG_FILENAME=$BOOK_IMAGE_OUTPUT_FILENAME_PREFIX/photos/$(echo "$BOOK_ISBN" | tr -d '"').jpeg
            curl -s "$BOOK_IMAGELINK" 2>/dev/null > $IMG_FILENAME

            echo "  - bookPhotoId: $BOOK_ID" >> $BOOK_IMAGE_OUTPUT_FILENAME_PREFIX"ALL_BOOK_IMAGES"$OUTPUT_FILENAME_POSTFIX
            echo "    isbn: $BOOK_ISBN" >> $BOOK_IMAGE_OUTPUT_FILENAME_PREFIX"ALL_BOOK_IMAGES"$OUTPUT_FILENAME_POSTFIX
            echo "    bookPhoto: " >> $BOOK_IMAGE_OUTPUT_FILENAME_PREFIX"ALL_BOOK_IMAGES"$OUTPUT_FILENAME_POSTFIX

        fi

        sleep 0.5
    done <<< "${CATALOGS_ISBNS[idx]}"

    echo "Done."
done