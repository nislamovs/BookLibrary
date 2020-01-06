package com.dbpopulator.app.domain.exceptions;

public class HistoryRecordNotFoundException extends RuntimeException {

    public HistoryRecordNotFoundException(String msg) {
        super(msg);
    }

    public HistoryRecordNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
