#!/usr/bin/env bash

#Here will be temporarily stored useful scripts for work

#docker run -d -p 27018:27017 mongo
#
#mongodump --host=localhost --port=27017 --db=booklibrary
#
#mongorestore --host=localhost --port=27018 --db=booklibrary ./booklibrary/


. ./constants.sh
. ./apiKeys.sh



    PUB_YEAR_INTERVAL="1980-2000"
    PUB_YEAR_START=$(echo "$PUB_YEAR_INTERVAL" | gawk -F"-" {'print $1'})
    PUB_YEAR_END=$(echo "$PUB_YEAR_INTERVAL" | gawk -F"-" {'print $2'})
    INTERVAL_SUBSTRING=""

    for (( PUB_YEAR=$PUB_YEAR_START; PUB_YEAR<=$PUB_YEAR_END; PUB_YEAR++ ))
    do
        INTERVAL_SUBSTRING="$INTERVAL_SUBSTRING&publicationyear=$PUB_YEAR"
    done

    echo "${INTERVAL_SUBSTRING}"
