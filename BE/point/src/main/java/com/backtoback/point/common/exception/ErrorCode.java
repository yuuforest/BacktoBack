package com.backtoback.point.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode { // Response body에 담기는 값 -> front에서 확인

    // common
    INTERNAL_SERVER_ERROR(500, "C001", "Server Error"),

    // betting
    POINT_LACK_ERROR(400, "C002", "포인트가 부족합니다."),
    REDIS_NOT_FOUND(400, "C003", "Redis에 해당하는 key 정보가 존재하지 않습니다."),
    BETTING_ALREADY_EXIST(400, "C004", "해당 경기에 이미 존재하는 베팅 기록이 있습니다"),

    // member & game & team
    MEMBER_NOT_FOUND(400, "C005", "해당하는 회원 ID 정보가 존재하지 않습니다."),
    GAME_NOT_FOUND(400, "C006", "해당하는 경기 ID 정보가 존재하지 않습니다. "),
    TEAM_NOT_FOUND(400, "C007", "해당하는 팀 ID 정보가 존재하지 않습니다. ");


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
