package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.ProcessingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProcessingControlRepository extends MongoRepository<ProcessingStatus, String> {

    Optional<ProcessingStatus> findByTableNameIgnoreCase(String tableName);
}
