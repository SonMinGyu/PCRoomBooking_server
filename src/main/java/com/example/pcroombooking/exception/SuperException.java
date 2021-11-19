package com.example.pcroombooking.exception;

import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;

public class SuperException extends RuntimeException{

    private CustomExceptionType customExceptionType;

    public SuperException(CustomExceptionType customExceptionType) {
        this.customExceptionType = customExceptionType;
    }
    public CustomExceptionType getCustomExceptionType() {
        return customExceptionType;
    }
}
