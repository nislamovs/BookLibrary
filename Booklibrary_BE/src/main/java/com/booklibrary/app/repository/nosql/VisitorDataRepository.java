package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.VisitorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorDataRepository extends MongoRepository<VisitorData, String> {

    Optional<VisitorData> findVisitorDataByVisitorId(String visitorId);

    Page<VisitorData> findAll(Pageable pageable);

    List<VisitorData> findAll();

    void deleteByVisitorId(String visitorId);

    Optional<VisitorData> findVisitorByVisitorId(String visitorId);
}
