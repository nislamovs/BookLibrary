package com.booklibrary.app.controllers.restricted;

import com.booklibrary.app.controllers.restricted.docs.IBookStorageController;
import com.booklibrary.app.converters.orikaConverters.ToResponseConverters;
import com.booklibrary.app.domain.requests.BookStorageRequest;
import com.booklibrary.app.services.BookStorageService;
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
@RequestMapping("/api/v1/book")
public class BookStorageController implements IBookStorageController {

    private final BookStorageService bookStorageService;

    @GetMapping("/storage/{bookId}")
    public ResponseEntity<?> getBooksInfoById(@PathVariable("bookId") @NotBlank String bookId) {
        log.info("Retrieving books info by book Id: {}", bookId);
        return ok(bookStorageService.getBooksInfoById(bookId).stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "bookNumber" })
    public ResponseEntity<?> getBookByNumber(@RequestParam(value="bookNumber") @Positive Integer bookNumber) {
        log.info("Retrieving book by book number: {}", bookNumber);
        return ok(toResponse(bookStorageService.getBookInfoByNumber(String.valueOf(bookNumber))));
    }

    @GetMapping(path = "/storage", params = { "isbn" })
    public ResponseEntity<?> getBooksInfoByIsbn(@RequestParam(value="isbn") @NotBlank String isbn) {
        log.info("Retrieving books info by isbn: {}", isbn);
        return ok(bookStorageService.getBooksInfoByIsbn(isbn).stream().map(ToResponseConverters::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "size", "page" })
    public ResponseEntity<?> getBooks( @DefaultValue("1") @RequestParam(value="page") int page,
                                       @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of books info. Page[{}], size[{}]", page, size);
        return ok(bookStorageService.getBooks(page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "author", "size", "page" })
    public ResponseEntity<?> getBooksByAuthor( @RequestParam(value="author", required = true) String author,
                                               @DefaultValue("1") @RequestParam(value="page") int page,
                                               @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of books info by author. Author[{}], page[{}], size[{}]", author, page, size);
        return ok(bookStorageService.getBooksByAuthor(author, page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "category", "size", "page" })
    public ResponseEntity<?> getBooksByCategory( @RequestParam(value="category", required = true) String category,
                                               @DefaultValue("1") @RequestParam(value="page") int page,
                                               @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of books info by category. Category[{}], page[{}], size[{}]", category, page, size);
        return ok(bookStorageService.getBooksByCategory(category, page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "status", "size", "page" })
    public ResponseEntity<?> getBooksByBookStatus( @RequestParam(value="status", required = true) String status,
                                               @DefaultValue("1") @RequestParam(value="page") int page,
                                               @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of books info by book status. Status[{}], page[{}], size[{}]", status, page, size);
        return ok(bookStorageService.getBooksByStatus(status, page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @GetMapping(path = "/storage", params = { "visitorId", "size", "page" })
    public ResponseEntity<?> getBooksByVisitorId( @RequestParam(value="visitorId", required = true) String visitorId,
                                               @DefaultValue("1") @RequestParam(value="page") int page,
                                               @DefaultValue("10") @RequestParam(value="size") int size) {
        log.info("Retrieving page of books info by book visitorId. VisitorId[{}], page[{}], size[{}]", visitorId, page, size);
        return ok(bookStorageService.getBooksByVisitorId(visitorId, page, size).getContent().stream()
            .map(ToResponseConverters::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/storage")
    public ResponseEntity<?> addNewBookSample(@Valid @RequestBody BookStorageRequest bookStorage) {
        log.info("Adding book sample : isbn number: {}", bookStorage.getIsbn());
        return ok(from(bookStorageService.addNewBookSample(bookStorage)));
    }

    @PutMapping("/storage")
    public ResponseEntity<?> editBookInfo(@Valid @RequestBody BookStorageRequest bookStorage) {
        log.info("Updating book by isbn number: {}", bookStorage.getIsbn());
        return ok(from(bookStorageService.updateBookInfo(bookStorage)));
    }

    @DeleteMapping(path = "/storage", params = { "isbn" })
    public ResponseEntity<?> deleteBooksByIsbn(@RequestParam(value="isbn") @NotBlank String isbn) {
        log.info("Deleting books by isbn: {}", isbn);
        bookStorageService.deleteBooksByIsbn(isbn);
        return (ResponseEntity<?>) ok();
    }

    @DeleteMapping(path = "/storage", params = { "booknumber" })
    public ResponseEntity<?> deleteBookByBookNumber(@RequestParam(value="booknumber") @NotBlank String booknumber) {
        log.info("Deleting book by book number: {}", booknumber);
        bookStorageService.deleteBookByBookNumber(booknumber);
        return (ResponseEntity<?>) ok();
    }
}
