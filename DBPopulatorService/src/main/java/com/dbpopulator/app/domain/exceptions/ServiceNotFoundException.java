package com.dbpopulator.app.domain.exceptions;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(String msg) {
        super(msg);
    }

    public ServiceNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
