package com.tzawartko.employee.exception;

public class SubordinatesCountToManagerException extends RuntimeException {

    public SubordinatesCountToManagerException() {
        super("Too many subordinates to manager.");
    }

    public SubordinatesCountToManagerException(String message) {
        super(message);
    }
}
