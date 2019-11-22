#!/usr/bin/env bash

. ./constants.sh

cd .. ;
mkdir -p ${DEBTS_OUTPUT_FILENAME_PREFIX}
OUTPUT_FNAME=${DEBTS_OUTPUT_FILENAME_PREFIX}"DEBTS"${OUTPUT_FILENAME_POSTFIX}

echo "debts:" > $OUTPUT_FNAME

for (( N=1; N<=$DEBT_COUNT; N++ )) {
    DEBT_ID=${N}
    BOOK_NUMBER=$(( N*5+1 ))
    VISITOR_ID=${N}
    HISTORY_ID=${N}

    OVERDUE_DAYS=$(shuf -i1-15 -n1)
    ACTUAL_PENALTY=''
    PAYMENTS="{}"

#   Book_data saving to file...
    echo "  - debtId: $DEBT_ID" >> $OUTPUT_FNAME
    echo "    bookNumber: $BOOK_NUMBER" >> $OUTPUT_FNAME
    echo "    visitorId: $VISITOR_ID" >> $OUTPUT_FNAME
    echo "    historyId: $HISTORY_ID" >> $OUTPUT_FNAME

    echo "    overdueDays: $OVERDUE_DAYS" >> $OUTPUT_FNAME
    echo "    actualPenalty: $ACTUAL_PENALTY" >> $OUTPUT_FNAME
    echo "    payments: $PAYMENTS" >> $OUTPUT_FNAME
}