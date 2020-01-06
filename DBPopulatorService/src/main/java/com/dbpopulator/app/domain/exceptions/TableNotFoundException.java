package com.dbpopulator.app.domain.exceptions;

public class TableNotFoundException extends RuntimeException {

    public TableNotFoundException(String msg) {
        super(msg);
    }

    public TableNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
