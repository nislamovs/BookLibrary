package com.dbpopulator.app.repository.nosql;

import com.dbpopulator.app.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookStorageRepository extends MongoRepository<Book, String> {


}
