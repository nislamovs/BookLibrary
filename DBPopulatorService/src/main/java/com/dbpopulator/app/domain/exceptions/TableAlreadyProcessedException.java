package com.dbpopulator.app.domain.exceptions;

public class TableAlreadyProcessedException extends RuntimeException {

    public TableAlreadyProcessedException(String msg) {
        super(msg);
    }

    public TableAlreadyProcessedException(String msg, Throwable t) {
        super(msg, t);
    }
}
