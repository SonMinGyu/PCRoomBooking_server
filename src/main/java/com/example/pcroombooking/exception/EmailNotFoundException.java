package com.example.pcroombooking.exception;

import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;

public class EmailNotFoundException extends SuperException {

    public EmailNotFoundException(CustomExceptionType customExceptionType) {
        super(customExceptionType);
    }

}
