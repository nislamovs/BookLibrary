package com.booklibrary.app.domain.exceptions;

public class PenaltyPlanNotFoundException extends RuntimeException {

    public PenaltyPlanNotFoundException(String msg) {
        super(msg);
    }

    public PenaltyPlanNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
