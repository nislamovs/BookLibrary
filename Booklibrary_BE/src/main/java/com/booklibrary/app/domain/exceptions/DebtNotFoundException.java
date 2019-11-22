package com.booklibrary.app.domain.exceptions;

public class DebtNotFoundException extends RuntimeException {

    public DebtNotFoundException(String msg) {
        super(msg);
    }

    public DebtNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
