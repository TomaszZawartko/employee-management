package com.tzawartko.employee.exception;

public class PeselException extends RuntimeException {

    public PeselException() {
        super("Can't create employee because PESEL is duplicated.");
    }

    public PeselException(String message) {
        super(message);
    }
}
