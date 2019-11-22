package com.booklibrary.app.services.interfaces;

import com.booklibrary.app.domain.requests.BookDataRequest;
import com.booklibrary.app.models.nosql.BookData;
import org.springframework.data.domain.Page;

public interface IBookDataService {

    BookData getBookDataById(String bookId);

    BookData getBookDataByBookNumber(String bookNumber);

    BookData getBookDataByIsbn(String isbn);

    Page<BookData> getBooks(int page, int size);

    Page<BookData> getBooksByAuthor(String author, int page, int size, boolean isSorted);

    Page<BookData> getBooksByCategory(String category, int page, int size, boolean isSorted);

    Page<BookData> getBooksByPublisher(String publisher, int page, int size, boolean isSorted);

    Page<BookData> getBooksByPubDate(String publishedDate, int page, int size, boolean isSorted);

    Page<BookData> getBooksByTitle(String title, int page, int size, boolean isSorted);

    BookData registerNewBook(BookDataRequest bookDataRequest);

    BookData updateBook(BookDataRequest bookData);

    void deleteBookByIsbn(String isbn);

    void deleteBookSampleByBookNumber(String bookNumber);
}
