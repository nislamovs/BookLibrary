package com.dbpopulator.app.domain.exceptions;

public class TableNotPreprocessedException extends RuntimeException {

    public TableNotPreprocessedException(String msg) {
        super(msg);
    }

    public TableNotPreprocessedException(String msg, Throwable t) {
        super(msg, t);
    }
}
