package com.booklibrary.app.repository.nosql;

import com.booklibrary.app.models.nosql.BookData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BookDataRepository extends MongoRepository<BookData, String> {

    Optional<BookData> findOneByBookId(String bookId);

    Optional<BookData> findOneByIsbn(String isbn);


    Optional<BookData> findBookByBookNumber(String bookNumber);

    Page<BookData> findByAuthorsContaining(String author, PageRequest of);

    Page<BookData> findByCategoriesContaining(String category, PageRequest of);

    Page<BookData> findByBookTitleContaining(String title, PageRequest of);

    Page<BookData> findByPublishedDate(Date publishedDate, PageRequest of);

    Page<BookData> findByPublisherContaining(String publisher, PageRequest of);

    void deleteBookDataByIsbn(String isbn);

}
