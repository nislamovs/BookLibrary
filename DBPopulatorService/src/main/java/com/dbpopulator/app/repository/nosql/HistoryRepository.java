package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.History;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

    Optional<History> findByHistoryId(String historyId);
}
