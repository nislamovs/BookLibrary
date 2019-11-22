package com.booklibrary.app.repository.sql.stuff;

public class HistoryRepositoryImpl {//implements CustomRepository {

//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    public HistoryRepositoryImpl(MongoTemplate mongoTemplate) {
//        super();
//        this.mongoTemplate = mongoTemplate;
//    }

//    @Override
    public void getHistoryByHistoryId(String historyId) {

//        LookupOperation lookupDebts = LookupOperation.newLookup()
//            .from("debt")
//            .localField("history.historyId")
//            .foreignField("debt.historyId")
//            .as("debt");
//
//        LookupOperation lookupPayments = LookupOperation.newLookup()
//            .from("payments")
//            .localField("history.debt.debtId")
//            .foreignField("payments.debtId")
//            .as("debt.payments");
//
//        AggregationResults<History> result = mongoTemplate.aggregate(
//            Aggregation.newAggregation(lookupDebts, unwind("$debt",true), lookupPayments),  "history", History.class);

//        return result.getUniqueMappedResult();
    }
}
