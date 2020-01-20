package com.dbpopulator.app.domain.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DumpNotFoundException extends RuntimeException {

    public DumpNotFoundException(String msg) {
        super(msg);
    }

    public DumpNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
