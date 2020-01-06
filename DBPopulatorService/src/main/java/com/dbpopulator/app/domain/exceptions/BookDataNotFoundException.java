package com.dbpopulator.app.domain.exceptions;

public class BookDataNotFoundException extends RuntimeException {

    public BookDataNotFoundException(String msg) {
        super(msg);
    }

    public BookDataNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
