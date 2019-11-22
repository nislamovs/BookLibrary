package com.booklibrary.app.domain.exceptions;

public class CardholderNotFoundException extends RuntimeException {

    public CardholderNotFoundException(String msg) {
        super(msg);
    }

    public CardholderNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
