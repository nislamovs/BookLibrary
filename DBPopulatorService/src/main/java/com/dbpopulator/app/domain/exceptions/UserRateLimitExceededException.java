package com.dbpopulator.app.domain.exceptions;

public class UserRateLimitExceededException extends RuntimeException {

    public UserRateLimitExceededException(String msg) {
        super(msg);
    }

    public UserRateLimitExceededException(String msg, Throwable t) {
        super(msg, t);
    }
}
