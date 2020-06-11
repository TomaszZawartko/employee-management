package com.tzawartko.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DefaultResponseStatusException {

    public static void throwNotAcceptableParameterException(String message, RuntimeException ex) {
        throw new ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE,
                message,
                ex);
    }

    public static void throwEmployeeBadRequestException(String message, RuntimeException ex) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                message,
                ex);
    }

}

