package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.VisitorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorDataRepository extends MongoRepository<VisitorData, String> {

    @Query("{ 'visitorId' : ?0 }")
    Optional<VisitorData> findByVisitorId(String visitorId);
}
