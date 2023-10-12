package com.company.kunuzdemo.repository;


public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}