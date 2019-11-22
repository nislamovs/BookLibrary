package com.booklibrary.app.domain.exceptions;

public class BookDataFoundException extends RuntimeException {

    public BookDataFoundException(String msg) {
        super(msg);
    }

    public BookDataFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
