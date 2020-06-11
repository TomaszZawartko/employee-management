package com.tzawartko.employee.exception;

public class DeleteEmployeeException extends RuntimeException {
    public DeleteEmployeeException() {
        super("Can't delet employee because he has some subordinates.");
    }

    public DeleteEmployeeException(String message) {
        super(message);
    }
}
