package com.booklibrary.app.domain.exceptions;

public class BookPhotoFoundException extends RuntimeException {

    public BookPhotoFoundException(String msg) {
        super(msg);
    }

    public BookPhotoFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
