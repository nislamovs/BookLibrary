package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.History;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

    @Aggregation(pipeline = {
        "{$lookup: {from: \"debt\",localField: \"historyId\",foreignField: \"historyId\",as: \"debt\"}}",
        "{$unwind: {path: \"$debt\", preserveNullAndEmptyArrays: true}}",
        "{$lookup: {from: \"payments\",localField: \"debt.debtId\",foreignField: \"debtId\",as: \"debt.payments\"}}",
        "{$match : {historyId : ?0}}"
    })
    Optional<History> findByHistoryId(String historyId);

    List<History> findByVisitorId(String visitorId);

    List<History> findByBookNumber(String booknumber);

    List<History> findByBookId(String bookId);

    void deleteByHistoryId(String historyId);
}
