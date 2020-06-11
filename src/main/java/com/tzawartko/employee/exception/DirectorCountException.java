package com.tzawartko.employee.exception;

public class DirectorCountException extends RuntimeException {

    public DirectorCountException() {
        super("Can't create new director, because the maximum number of employees in this position has been reached");
    }

    public DirectorCountException(String message) {
        super(message);
    }
}
