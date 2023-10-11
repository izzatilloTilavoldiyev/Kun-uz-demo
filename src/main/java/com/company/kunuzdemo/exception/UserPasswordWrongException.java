package com.company.kunuzdemo.exception;

public class UserPasswordWrongException extends RuntimeException{
    public UserPasswordWrongException(String message) {
        super(message);
    }

}
