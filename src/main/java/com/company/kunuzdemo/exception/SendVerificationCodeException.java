package com.company.kunuzdemo.exception;


public class SendVerificationCodeException extends RuntimeException{
    public SendVerificationCodeException(String message) {
        super(message);
    }
}