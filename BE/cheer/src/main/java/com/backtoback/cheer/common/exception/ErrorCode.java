package com.backtoback.cheer.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode { // Response body에 담기는 값 -> front에서 확인

    // common
    INTERNAL_SERVER_ERROR(500, "C001", "Server Error"),
    ENTITY_NOT_FOUND(400, "C002", "오류가 발생했습니다."),
    REDIS_NOT_FOUND(400, "C003", "오류가 발생했습니다."),

    // member & game & team
    GAME_NOT_YET_START(400, "C006", "경기 시작 전입니다.");


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
