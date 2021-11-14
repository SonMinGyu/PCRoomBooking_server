package com.example.pcroombooking.exception.exceptionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private int errorCode;
    private int httpStatus;
    private String errorMessage;
}
