package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class RedisNotFoundException extends BusinessException {

    public RedisNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RedisNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
