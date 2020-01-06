package com.dbpopulator.app.repository.nosql;


import com.dbpopulator.app.models.BookData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDataRepository extends MongoRepository<BookData, String> {

    Optional<BookData> findByBookId(String bookId);

    @Query(value = "{totalCount: 0}")
    List<BookData> findUnprocessed(Pageable pageable);
}
