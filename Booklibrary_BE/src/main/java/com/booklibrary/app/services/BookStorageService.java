package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.BookNotFoundException;
import com.booklibrary.app.domain.requests.BookStorageRequest;
import com.booklibrary.app.models.nosql.Book;
import com.booklibrary.app.repository.nosql.BookDataRepository;
import com.booklibrary.app.repository.nosql.BookStorageRepository;
import com.booklibrary.app.services.interfaces.IBookStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.booklibrary.app.converters.orikaConverters.ToDocumentConverters.toDocument;
import static java.lang.String.format;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStorageService implements IBookStorageService {

    private final BookStorageRepository bookStorageRepository;
    private final BookDataRepository bookDataRepository;

    @Override
    public List<Book> getBooksInfoById(String bookId) {
        return bookStorageRepository.findByBookId(bookId);
//            .orElseThrow(() -> new BookNotFoundException(format("Books with Id [%s] was not found.", bookId)));
    }

    @Override
    public Book getBookInfoByNumber(String bookNumber) {
        return bookStorageRepository.findOneByBookNumber(bookNumber)
            .orElseThrow(() -> new BookNotFoundException(format("Books with number [%s] was not found.", bookNumber)));
    }

    @Override
    public List<Book> getBooksInfoByIsbn(String isbn) {
        return bookStorageRepository.findByIsbn(isbn);
//            .orElseThrow(() -> new BookNotFoundException(format("Books with isbn [%s] was not found.", isbn)));
    }

    @Override
    public Page<Book> getBooks(int page, int size) {
        return bookStorageRepository.findAll(of(page, size));
    }

    @Override
    public Page<Book> getBooksByAuthor(String author, int page, int size) {
        return bookStorageRepository.findByAuthorsContaining(author, of(page, size));
    }

    @Override
    public Page<Book> getBooksByCategory(String category, int page, int size) {
        return bookStorageRepository.findByCategoryContaining(category, of(page, size));
    }

    @Override
    public Page<Book> getBooksByStatus(String status, int page, int size) {
        return bookStorageRepository.findByStatus(status, of(page, size));
    }

    @Override
    public Page<Book> getBooksByVisitorId(String visitorId, int page, int size) {
        return bookStorageRepository.findByVisitorId(visitorId, of(page, size));
    }

    @Override
    public Book addNewBookSample(BookStorageRequest book) {
        return bookStorageRepository.save(toDocument(book));
    }

    @Override
    public Book updateBookInfo(BookStorageRequest book) {
        return bookStorageRepository.save(toDocument(book));
    }

    @Override
    public void deleteBooksByIsbn(String isbn) {
        bookStorageRepository.deleteByIsbn(isbn);
            //.orElseThrow(() -> new BookNotFoundException(format("Book with ISBN number [%s] was not found.", isbn)));
    }

    @Override
    public void deleteBookByBookNumber(String bookNumber) {
        bookStorageRepository.deleteByBookNumber(bookNumber);
            //.orElseThrow(() -> new BookNotFoundException(format("Book with number [%s] was not found.", isbn)));
    }
}
