package com.example.pcroombooking.exception.handler;

import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionResponse.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SuperException.class)
    public ExceptionResponse superException(SuperException superException) {
        return superException.getCustomExceptionType().toExceptionResponse();
    }

    /////////////////// exception을 하나씩 따로 만들필요가 없지 않을까?
//    @ExceptionHandler(EmailNotFoundSuperException.class)
//    public ExceptionResponse emailNotFoundException(EmailNotFoundSuperException emailNotFoundException) {
//        return emailNotFoundException.getCustomExceptionType().toExceptionResponse();
//    }
//
//    @ExceptionHandler(WrongPasswordSuperException.class)
//    public ExceptionResponse wrongPasswordException(WrongPasswordSuperException wrongPasswordSuperException) {
//        return wrongPasswordSuperException.getCustomExceptionType().toExceptionResponse();
//    }
//
//    @ExceptionHandler(CryptogramNotFoundException.class)
//    public ExceptionResponse wrongPasswordException(CryptogramNotFoundException cryptogramNotFoundException) {
//        return cryptogramNotFoundException.getCustomExceptionType().toExceptionResponse();
//    }

}
