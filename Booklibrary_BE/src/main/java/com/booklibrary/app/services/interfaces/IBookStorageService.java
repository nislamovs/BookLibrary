package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.BookStorageRequest;
import com.booklibrary.app.models.nosql.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBookStorageService {

    List<Book> getBooksInfoById(String bookId);

    Book getBookInfoByNumber(String bookNumber);

    List<Book> getBooksInfoByIsbn(String isbn);

    Page<Book> getBooks(int page, int size);

    Page<Book> getBooksByAuthor(String author, int page, int size);

    Page<Book> getBooksByCategory(String category, int page, int size);

    Page<Book> getBooksByStatus(String status, int page, int size);

    Page<Book> getBooksByVisitorId(String visitorId, int page, int size);

    Book addNewBookSample(BookStorageRequest book);

    Book updateBookInfo(BookStorageRequest book);

    void deleteBooksByIsbn(String isbn);

    void deleteBookByBookNumber(String bookNumber);
}
