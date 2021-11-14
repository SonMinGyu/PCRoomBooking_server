package com.example.pcroombooking.exception.exceptionType;

import com.example.pcroombooking.exception.exceptionResponse.ExceptionResponse;
import lombok.Data;

import javax.swing.plaf.PanelUI;

public enum CustomExceptionType {

    EMAIL_NOT_FOUND_EXCEPTION(1000, 200, "해당 사용자를 찾을 수 없습니다."), // login email 오류시 발생
    WRONG_PASSWORD_EXCEPTION(1001, 200, "비밀번호가 틀렸습니다."), // login password 오류시 발생
    CRYPTOGRAM_NOT_FOUNT_EXCEPTION(1002, 200, "문자열이 틀렸습니다."), // register cryptogram 오류시 발생
    CRYPTOGRAM_EXPIRED_EXCEPTION(1003, 200, "문자열이 만료되었습니다. 이메일 인증요청을 다시 진행해 주세요."),
    // register cryptogram 만료시 발생
    CRYPTOGRAM_NOT_VERIFIED_EXCEPTION(1004, 200, "문자열 인증이 완료되지 않았습니다. 인증을 완료해주세요."),
    // register cryptogram 인증이 완료되지 않았을 때 발생
    CRYPTOGRAM_SAVE_EXCEPTION(1005, 200, "오류로 인해 문자열이 저장되지 않았습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    private CustomExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ExceptionResponse toExceptionResponse() {
        return ExceptionResponse.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .build();
    }
}
