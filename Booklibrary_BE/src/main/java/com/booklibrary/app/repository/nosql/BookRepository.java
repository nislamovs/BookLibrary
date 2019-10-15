package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByAuthor(String author);
}
