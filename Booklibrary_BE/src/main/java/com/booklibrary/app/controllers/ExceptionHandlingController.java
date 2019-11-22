package com.booklibrary.app.controllers;

import com.booklibrary.app.domain.exceptions.BookNotFoundException;
import com.booklibrary.app.domain.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @Value("${application.support.email}")
    private String supportEmail;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({BookNotFoundException.class})
    public ErrorResponse handleNotFoundException(RuntimeException ex) {

        log.info("Bad request : Requested data not found " + ex.getMessage());

        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ValidationException.class,
            IllegalArgumentException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class})
    public ErrorResponse handleBadRequests(Exception ex) {

        log.info("Bad request : Request error " + ex.getMessage());

        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class})
    public ResponseEntity<List<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {

        log.info("Bad request : Validation error " + ex.getMessage());

        return new ResponseEntity<List<ErrorResponse>>(processError(ex), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex) {

        log.error("Internal exception: ", ex);

        final String message = "Unexpected problem encountered. Please contact support team (" + supportEmail + ") with timestamp ["
                + now() + "] and short description.";

        return new ErrorResponse(message);
    }

    private List<ErrorResponse> processError(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().stream()
                .forEach(err -> errors.add(new ErrorResponse(err.getDefaultMessage())));

        return errors;
    }
}
