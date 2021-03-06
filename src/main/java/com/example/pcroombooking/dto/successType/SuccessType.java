package com.example.pcroombooking.dto.successType;

import com.example.pcroombooking.exception.exceptionResponse.ExceptionResponse;

public enum SuccessType {

    // exception = 1000번대, success = 2000번대
    LOGIN_SUCCESS(200, 2000, "LOGIN_SUCCESS", "로그인에 성공하였습니다."),
    REGISTER_SUCCESS(200, 2001,"REGISTER_SUCCESS", "회원가입에 성공하였습니다."),
    EMAIL_SEND_SUCCESS(200, 2002,"EMAIL_SEND_SUCCESS",  "인증 이메일 발송에 성공하였습니다."),
    CRYPTOGRAM_VERIFY_SUCCESS(200, 2003, "CRYPTOGRAM_VERIFY_SUCCESS", "문자열 인증에 성공하였습니다."),
    SEAT_SEARCH_SUCCESS(200, 2004, "SEAT_SEARCH_SUCCESS", " 좌석 검색에 성공하였습니다."),
    PCROOM_SEARCH_SUCCESS(200, 2005, "PCROOM_SEARCH_SUCCESS", "모든 PC실 검색에 성공하였습니다."),
    CONFERENCEROOM_SEARCH_SUCCESS(200, 2006, "CONFERENCEROOM_SEARCH_SUCCESS", "팀플실 검색에 성공하였습니다."),
    CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS(200, 2007, "CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS", "예약 검색에 성공하였습니다."),
    CONFERENCEROOM_RESERVE_SUCCESS(200, 2008, "CONFERENCEROOM_RESERVE_SUCCESS", "예약에 성공하였습니다."),
    PCROOM_ADD_SUCCESS(200, 2009, "PCROOM_ADD_SUCCESS", "PC실 추가에 성공하였습니다."),
    PCROOM_GET_SUCCESS(200, 2010, "PCROOM_GET_SUCCESS", "해당 PC실 찾기에 성공하였습니다."),
    PCROOM_UPDATE_SUCCESS(200, 2011, "PCROOM_UPDATE_SUCCESS", "PC실 업데이트에 성공하였습니다."),
    SEAT_ADD_SUCCESS(200, 2012, "SEAT_ADD_SUCCESS", "좌석 추가에 성공하였습니다."),
    SEAT_UPDATE_SUCCESS(200, 2013, "SEAT_UPDATE_SUCCESS", "좌석 업데이트에 성공하였습니다."),
    PCROOM_NAME_CAN_USE_SUCCESS(200, 2014, "PCROOM_NAME_CAN_USE_SUCCESS", "해당 PC실 이름을 사용할 수 있습니다.");

    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;

    private SuccessType(int httpStatus, int responseCode, String result, String responseMessage) {
        this.responseCode = responseCode;
        this.httpStatus = httpStatus;
        this.result = result;
        this.responseMessage = responseMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResult() {
        return result;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
