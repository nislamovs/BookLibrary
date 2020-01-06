package com.dbpopulator.app.domain.exceptions;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String msg) {
        super(msg);
    }

    public PaymentNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
