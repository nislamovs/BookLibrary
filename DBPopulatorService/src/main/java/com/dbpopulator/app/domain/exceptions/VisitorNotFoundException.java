package com.dbpopulator.app.domain.exceptions;

public class VisitorNotFoundException extends RuntimeException {

    public VisitorNotFoundException(String msg) {
        super(msg);
    }

    public VisitorNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
