package com.company.kunuzdemo.exception;

public class DuplicateValueException extends RuntimeException{

    public DuplicateValueException(String message) {
        super(message);
    }
}