package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IBookDataController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.BookDataRequest;
import com.booklibrary.app.services.BookDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.util.stream.Collectors;

import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.from;
import static com.booklibrary.app.converters.orikaConverters.ToResponseConverters.toResponse;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookDataController implements IBookDataController {

    private final BookDataService bookDataService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getBookDataById(@PathVariable("bookId") @NotBlank String bookId) {
        log.info("Retrieving book data by bookId: {}", bookId);
        return ok(toResponse(bookDataService.getBookDataById(bookId)));
    }

    @GetMapping(path = "/book", params = { "bookNumber" })
    public ResponseEntity<?> getBookDataByNumber(@RequestParam(value="bookNumber") @Positive Integer bookNumber) {
        log.info("Retrieving book data by booknumber: {}", bookNumber);
        return ok(toResponse(bookDataService.getBookDataByBookNumber(String.valueOf(bookNumber))));
    }

    @GetMapping(path = "/book", params = { "isbn" })
    public ResponseEntity<?> getBookDataByIsbn(@RequestParam(value="isbn") @NotBlank String isbn) {
        log.info("Retrieving book data by isbn: {}", isbn);
        return ok(toResponse(bookDataService.getBookDataByIsbn(isbn)));
    }

    @GetMapping(path = "/book", params = { "size", "page" })
    public ResponseEntity<?> getBooks( @DefaultValue("1")  @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data . Page[{}], size[{}]", page, size);
        return ok(bookDataService.getBooks(page, size).getContent().stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping(path = "/book", params = { "author", "size", "page", "isSorted" })
    public ResponseEntity<?> getBooksByAuthor( @RequestParam(value="author", required = true) String author,
                                       @DefaultValue("false") @RequestParam(value="isSorted", required = true) boolean isSorted,
                                       @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data by author's name.Author[{}], page[{}], size[{}], isSorted[{}]",author, page, size, isSorted);
        return ok(bookDataService.getBooksByAuthor(author, page, size, isSorted).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/book", params = { "category", "size", "page", "isSorted" })
    public ResponseEntity<?> getBooksByCategory( @RequestParam(value="category", required = true) String category,
                                       @DefaultValue("false") @RequestParam(value="isSorted", required = true) boolean isSorted,
                                       @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data by category.Category[{}], page[{}], size[{}], isSorted[{}]",category, page, size, isSorted);
        return ok(bookDataService.getBooksByCategory(category, page, size, isSorted).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/book", params = { "publisher", "size", "page", "isSorted" })
    public ResponseEntity<?> getBooksByPublisher( @RequestParam(value="publisher", required = true) String publisher,
                                       @DefaultValue("false") @RequestParam(value="isSorted", required = true) boolean isSorted,
                                       @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data by publisher.Publisher[{}], page[{}], size[{}], isSorted[{}]", publisher, page, size, isSorted);
        return ok(bookDataService.getBooksByPublisher(publisher, page, size, isSorted).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/book", params = { "publishedDate", "size", "page", "isSorted" })
    public ResponseEntity<?> getBooksByPubDate( @RequestParam(value="publishedDate", required = true) String publishedDate,
                                       @DefaultValue("false") @RequestParam(value="isSorted", required = true) boolean isSorted,
                                       @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data by publishing date.PublishedDate[{}], page[{}], size[{}], isSorted[{}]", publishedDate, page, size, isSorted);
        return ok(bookDataService.getBooksByPubDate(publishedDate, page, size, isSorted).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }


    @GetMapping(path = "/book", params = { "title", "size", "page", "isSorted" })
    public ResponseEntity<?> getBooksByTitle( @RequestParam(value="title", required = true) String title,
                                       @DefaultValue("false") @RequestParam(value="isSorted", required = true) boolean isSorted,
                                       @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of book data by title.Title[{}], page[{}], size[{}], isSorted[{}]", title, page, size, isSorted);
        return ok(bookDataService.getBooksByTitle(title, page, size, isSorted).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/book")
    public ResponseEntity<?> registerNewBook(@Valid @RequestBody BookDataRequest book) {
        log.info("Registering book : isbn number: {} ; title : {}", book.getIsbn(), book.getBookTitle());
        return ok(from(bookDataService.registerNewBook(book)));
    }

    @PutMapping("/book")
    public ResponseEntity<?> editBookInfo(@Valid @RequestBody BookDataRequest book) {
        log.info("Updating book data by isbn number: {}", book.getIsbn());
        return ok(from(bookDataService.updateBook(book)));
    }

    @DeleteMapping(path = "/book", params = { "isbn" })
    public ResponseEntity<?> deleteBookByIsbn(@RequestParam(value="isbn") @NotBlank String isbn) {
        log.info("Deleting book by isbn: {}", isbn);
        bookDataService.deleteBookByIsbn(isbn);
        return (ResponseEntity<?>) ok();
    }

    @DeleteMapping(path = "/book", params = { "booknumber" })
    public ResponseEntity<?> deleteBookByBookNumber(@RequestParam(value="booknumber") @NotBlank String booknumber) {
        log.info("Deleting book by book number: {}", booknumber);
        bookDataService.deleteBookSampleByBookNumber(booknumber);
        return (ResponseEntity<?>) ok();
    }
}
