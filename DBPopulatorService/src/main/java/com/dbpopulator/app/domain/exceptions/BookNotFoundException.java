package com.dbpopulator.app.domain.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String msg) {
        super(msg);
    }

    public BookNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
