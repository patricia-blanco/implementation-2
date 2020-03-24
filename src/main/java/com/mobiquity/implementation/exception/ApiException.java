package com.mobiquity.implementation.exception;

public class ApiException extends Exception {

    public ApiException(String message, Exception e) {
        super(message, e);
    }

    public ApiException(String message) {
        super(message);
    }
}
