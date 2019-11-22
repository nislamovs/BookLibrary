package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookStorageRepository extends MongoRepository<Book, String> {

    List<Book> findByIsbn(String isbn);

    Optional<Book> findOneByBookNumber(String bookNumber);

    List<Book> findByBookId(String bookId);

    Page<Book> findAll(PageRequest of);
    Page<Book> findByAuthorsContaining(String author, PageRequest of);
    Page<Book> findByCategoryContaining(String category, PageRequest of);
    Page<Book> findByStatus(String status, PageRequest of);
    Page<Book> findByVisitorId(String visitorId, PageRequest of);

    void deleteByBookNumber(String bookNumber);

    void deleteByIsbn(String isbn);
}
