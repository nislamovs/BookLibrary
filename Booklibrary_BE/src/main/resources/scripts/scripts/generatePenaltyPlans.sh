#!/usr/bin/env bash

. ./constants.sh
cd .. ;
mkdir -p ${PENALTY_OUTPUT_FILENAME_PREFIX}
OUTPUT_FNAME=${PENALTY_OUTPUT_FILENAME_PREFIX}"PENALTY_PLANS"${OUTPUT_FILENAME_POSTFIX}

echo "penaltyPlans:" > $OUTPUT_FNAME

echo "  - planId: 1" >> $OUTPUT_FNAME
echo "    penaltyType: LITE" >> $OUTPUT_FNAME
echo "    basicPenalty: \"EUR 5.00\"" >> $OUTPUT_FNAME
echo "    penaltyPerDay: \"EUR 1.00\"" >> $OUTPUT_FNAME
echo "    multiplierCoefficient: 1.0" >> $OUTPUT_FNAME

echo "  - planId: 2" >> $OUTPUT_FNAME
echo "    penaltyType: MEDIUM" >> $OUTPUT_FNAME
echo "    basicPenalty: \"EUR 10.00\"" >> $OUTPUT_FNAME
echo "    penaltyPerDay: \"EUR 2.00\"" >> $OUTPUT_FNAME
echo "    multiplierCoefficient: 1.1" >> $OUTPUT_FNAME

echo "  - planId: 3" >> $OUTPUT_FNAME
echo "    penaltyType: HARD" >> $OUTPUT_FNAME
echo "    basicPenalty: \"EUR 15.00\"" >> $OUTPUT_FNAME
echo "    penaltyPerDay: \"EUR 3.00\"" >> $OUTPUT_FNAME
echo "    multiplierCoefficient: 1.3" >> $OUTPUT_FNAME

echo "  - planId: 4" >> $OUTPUT_FNAME
echo "    penaltyType: CRUEL" >> $OUTPUT_FNAME
echo "    basicPenalty: \"EUR 20.00\"" >> $OUTPUT_FNAME
echo "    penaltyPerDay: \"EUR 5.00\"" >> $OUTPUT_FNAME
echo "    multiplierCoefficient: 1.5" >> $OUTPUT_FNAME