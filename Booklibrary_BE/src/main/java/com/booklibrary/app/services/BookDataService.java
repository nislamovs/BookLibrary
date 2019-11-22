package com.booklibrary.app.services;

import com.booklibrary.app.domain.exceptions.BookNotFoundException;
import com.booklibrary.app.domain.requests.BookDataRequest;
import com.booklibrary.app.models.nosql.BookData;
import com.booklibrary.app.repository.nosql.BookDataRepository;
import com.booklibrary.app.repository.nosql.BookStorageRepository;
import com.booklibrary.app.services.interfaces.IBookDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;

import static com.booklibrary.app.converters.orikaConverters.ToDocumentConverters.toDocument;
import static java.lang.String.format;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookDataService implements IBookDataService {

    private final BookDataRepository bookDataRepository;
    private final BookStorageRepository bookStorageRepository;

    @Override
    public BookData getBookDataById(String bookId) {
        return bookDataRepository.findOneByBookId(bookId)
            .orElseThrow(() -> new BookNotFoundException(format("Book with Id [%s] was not found.", bookId)));
    }

    @Override
    public BookData getBookDataByBookNumber(String bookNumber) {
        return bookDataRepository.findBookByBookNumber(bookNumber)
            .orElseThrow(() -> new BookNotFoundException(format("Book with number [%s] was not found.", bookNumber)));
    }

    @Override
    public BookData getBookDataByIsbn(String isbn) {
        return bookDataRepository.findOneByIsbn(isbn)
            .orElseThrow(() -> new BookNotFoundException(format("Book with isbn [%s] was not found.", isbn)));
    }

    @Override
    public Page<BookData> getBooks(int page, int size) {
        return bookDataRepository.findAll(of(page, size));
    }

    @Override
    public Page<BookData> getBooksByAuthor(String author, int page, int size, boolean isSorted) {

        Sort sortOptions = isSorted ? Sort.by(Sort.Direction.DESC, "popularityRate")
                                    : Sort.unsorted();

        return bookDataRepository.findByAuthorsContaining(author, of(page, size, sortOptions));
    }

    @Override
    public Page<BookData> getBooksByCategory(String category, int page, int size, boolean isSorted) {

        Sort sortOptions = isSorted ? Sort.by(Sort.Direction.DESC, "popularityRate")
            : Sort.unsorted();

        return bookDataRepository.findByCategoriesContaining(category, of(page, size, sortOptions));
    }

    @Override
    public Page<BookData> getBooksByPublisher(String publisher, int page, int size, boolean isSorted) {

        Sort sortOptions = isSorted ? Sort.by(Sort.Direction.DESC, "popularityRate")
            : Sort.unsorted();

        return bookDataRepository.findByPublisherContaining(publisher, of(page, size, sortOptions));
    }

    @Override
    public Page<BookData> getBooksByPubDate(String publishedDate, int page, int size, boolean isSorted) {

        Sort sortOptions = isSorted ? Sort.by(Sort.Direction.DESC, "popularityRate")
            : Sort.unsorted();

        return bookDataRepository.findByPublishedDate(Date.valueOf(publishedDate), of(page, size, sortOptions));
    }

    @Override
    public Page<BookData> getBooksByTitle(String title, int page, int size, boolean isSorted) {

        Sort sortOptions = isSorted ? Sort.by(Sort.Direction.DESC, "popularityRate")
            : Sort.unsorted();

        return bookDataRepository.findByBookTitleContaining(title, of(page, size, sortOptions));
    }

    @Override
    public BookData registerNewBook(BookDataRequest bookDataRequest) {
        return bookDataRepository.save(toDocument(bookDataRequest));
    }

    @Override
    public BookData updateBook(BookDataRequest bookData) {
        return bookDataRepository.save(toDocument(bookData));
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        bookDataRepository.deleteBookDataByIsbn(isbn);
//            .orElseThrow(() -> new BookNotFoundException(format("Book with ISBN number [%s] was not found.", isbn)));
    }

    @Override
    public void deleteBookSampleByBookNumber(String bookNumber) {
        bookStorageRepository.deleteByBookNumber(bookNumber);
//            .orElseThrow(() -> new BookNotFoundException(format("Book with number [%s] was not found.", isbn)));
    }
}
