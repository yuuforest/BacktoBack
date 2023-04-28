package com.backtoback.point.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode { // Response body에 담기는 값 -> front에서 확인

    // common
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),

    // game
    GAME_NOT_FOUND(400, "C001", "해당하는 정보가 존재하지 않았습니다. "),
    TEAM_NOT_MATCH(400, "C002", "등록되어 있는 경기의 팀 정보와 입력된 팀 정보가 맞지 않습니다. ");

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
