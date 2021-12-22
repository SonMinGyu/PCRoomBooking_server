package com.example.pcroombooking.exception.exceptionType;

import com.example.pcroombooking.exception.exceptionResponse.ExceptionResponse;

public enum CustomExceptionType {

    // exception = 1000번대, success = 2000번대
    EMAIL_NOT_FOUND_EXCEPTION(200, 1000, "EMAIL_NOT_FOUND_EXCEPTION", "해당 사용자를 찾을 수 없습니다."), // login email 오류시 발생
    WRONG_PASSWORD_EXCEPTION(200, 1001,"WRONG_PASSWORD_EXCEPTION", "비밀번호가 틀렸습니다."), // login password 오류시 발생
    CRYPTOGRAM_NOT_FOUNT_EXCEPTION(200, 1002,"CRYPTOGRAM_NOT_FOUNT_EXCEPTION",  "문자열이 틀렸습니다."), // register cryptogram 오류시 발생
    CRYPTOGRAM_EXPIRED_EXCEPTION(200, 1003, "CRYPTOGRAM_EXPIRED_EXCEPTION", "문자열이 만료되었습니다. 이메일 인증요청을 다시 진행해 주세요."),
    // register cryptogram 만료시 발생
    CRYPTOGRAM_NOT_VERIFIED_EXCEPTION(200, 1004,"CRYPTOGRAM_NOT_VERIFIED_EXCEPTION",  "문자열 인증이 완료되지 않았습니다. 인증을 완료해주세요."),
    // register cryptogram 인증이 완료되지 않았을 때 발생
    CRYPTOGRAM_SAVE_EXCEPTION(200, 1005, "CRYPTOGRAM_SAVE_EXCEPTION", "오류로 인해 문자열이 저장되지 않았습니다."), // 문자열 저장 실패
    PCROOM_NOT_FOUND_EXCEPTION(200, 1006,"PCROOM_NOT_FOUND_EXCEPTION",  "검색된 PC실이 없습니다."), // PC실이 검색되지 않았을 때
    SEAT_NOT_IN_PCROOM_EXCEPTION(200, 1007,"SEAT_NOT_IN_PCROOM_EXCEPTION",  "해당 PC실의 좌석이 검색되지 않았습니다."), // 해당 PC실의 좌석이 없을 때
    CONFERENCEROOM_NOT_FOUND_EXCEPTION(200, 1008,"CONFERENCEROOM_NOT_FOUND_EXCEPTION",  "검색된 팀플실이 없습니다."), // 팀플실이 검색되지 않았을 때
    CONFERENCEROOM_NOT_EXIST_EXCEPTION(200, 1009,"CONFERENCEROOM_NOT_EXIST_EXCEPTION",  "예약하려는 팀플실이 존재하지 않습니다."), // 예약하려는 팀플실이 존재하지 않을 때
    PCROOM_NOT_EXIST_EXCEPTION(200, 1010,"PCROOM_NOT_EXIST_EXCEPTION",  "해당 PC실이 존재하지 않습니다."), // 예약하려는 PC실이 존재하지 않울 때
    SEAT_SAVE_FAIL_EXCEPTION(200, 1011,"SEAT_SAVE_FAIL_EXCEPTION",  "좌석 생성에 실패하셨습니다. 다시 시도해주세요."), // 좌석 생성 실패
    CONFERENCEROOM_RESERVATION_SAVE_FAIL_EXCEPTION(200, 1012,"CONFERENCEROOM_RESERVATION_SAVE_FAIL_EXCEPTION",  "팀플실 예약에 실패하셨습니다. 다시 시도해주세요."), // 팀플실 예약 실패
    CONFERENCEROOM_RESERVATION_NOT_FOUND_EXCEPTION(200, 1013,"CONFERENCEROOM_RESERVATION_NOT_FOUND_EXCEPTION",  "해당 팀플실 예약이 아직 없습니다."), // 해당 팀플실 예약이 아직 없을 때
    PCROOM_SEARCH_FAIL_EXCEPTION(200, 1014,"PCROOM_SEARCH_FAIL_EXCEPTION",  "선택하신 PC실이 없습니다. 새로고침 후 다시 시도해주세요."), // 선택한 PC실이 없을 때
    SEAT_NOT_FOUND_EXCEPTION(200, 1015,"SEAT_NOT_FOUND_EXCEPTION",  "해당 좌석을 찾을 수 없습니다."), // 업데이트 할 좌석을 찾지 못했을 때
    SEAT_ALREADY_RESERVED_EXCEPTION(200, 1016,"SEAT_ALREADY_RESERVED_EXCEPTION",  "해당 좌석은 이미 예약되었습니다. 새로고침 후 다시 시도해주세요"); // 업데이트 할 좌석을 찾지 못했을 때

    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;

    private CustomExceptionType( int httpStatus, int responseCode, String result, String responseMessage) {
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

    public ExceptionResponse toExceptionResponse() {
        return ExceptionResponse.builder()
                .httpStatus(httpStatus)
                .responseCode(responseCode)
                .result(result)
                .responseMessage(responseMessage)
                .build();
    }
}
