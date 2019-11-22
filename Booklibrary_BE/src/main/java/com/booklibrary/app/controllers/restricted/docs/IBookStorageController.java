package com.booklibrary.app.controllers.restricted.docs;

import com.booklibrary.app.domain.requests.BookStorageRequest;
import com.booklibrary.app.domain.responses.ErrorResponse;
import com.booklibrary.app.domain.responses.responses.BookDataResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;


public interface IBookStorageController {

    @ApiOperation(
        httpMethod = "GET",
        notes = "Resource to get book by id",
        value = "Get book by id.",
        response = BookDataResponse.class,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book retrieved.", response = BookDataResponse.class),
        @ApiResponse(code = 400, message = "Something went wrong.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Book not found.", response = ErrorResponse.class)
    })
    ResponseEntity<?> getBooksInfoById(@ApiParam(value = "Book id", required = true, name = "Book id") @PathVariable("bookId") @NotBlank String bookId);

    @ApiOperation(
        httpMethod = "GET",
        notes = "Resource to get book by number",
        value = "Get book by number.",
        response = BookDataResponse.class,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book retrieved.", response = BookDataResponse.class),
        @ApiResponse(code = 400, message = "Something went wrong.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Book not found.", response = ErrorResponse.class)
    })
    ResponseEntity<?> getBookByNumber(@ApiParam(value = "Book number", required = true, name = "Book number") @PathParam("bookNumber") @Positive Integer bookNumber);

    ResponseEntity<?> getBooksInfoByIsbn(@RequestParam(value="isbn") @NotBlank String isbn);

    ResponseEntity<?> getBooks( @DefaultValue("1") @RequestParam(value="page") int page,
                                @DefaultValue("10") @RequestParam(value="size") int size);

    ResponseEntity<?> getBooksByAuthor( @RequestParam(value="author", required = true) String author,
                                        @DefaultValue("1") @RequestParam(value="page") int page,
                                        @DefaultValue("10") @RequestParam(value="size") int size);

    ResponseEntity<?> getBooksByCategory( @RequestParam(value="category", required = true) String category,
                                        @DefaultValue("1") @RequestParam(value="page") int page,
                                        @DefaultValue("10") @RequestParam(value="size") int size);

    ResponseEntity<?> getBooksByBookStatus( @RequestParam(value="status", required = true) String status,
                                            @DefaultValue("1") @RequestParam(value="page") int page,
                                            @DefaultValue("10") @RequestParam(value="size") int size);

    ResponseEntity<?> getBooksByVisitorId( @RequestParam(value="visitorId", required = true) String visitorId,
                                           @DefaultValue("1") @RequestParam(value="page") int page,
                                           @DefaultValue("10") @RequestParam(value="size") int size);

    ResponseEntity<?> addNewBookSample(@Valid @RequestBody BookStorageRequest book);

    ResponseEntity<?> editBookInfo(@Valid @RequestBody BookStorageRequest book);

    ResponseEntity<?> deleteBooksByIsbn(@RequestParam(value="isbn") @NotBlank String isbn);

    ResponseEntity<?> deleteBookByBookNumber(@RequestParam(value="booknumber") @NotBlank String booknumber);
}
