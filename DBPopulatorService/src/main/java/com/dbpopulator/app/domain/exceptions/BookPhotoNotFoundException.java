package com.dbpopulator.app.domain.exceptions;

public class BookPhotoNotFoundException extends RuntimeException {

    public BookPhotoNotFoundException(String msg) {
        super(msg);
    }

    public BookPhotoNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
