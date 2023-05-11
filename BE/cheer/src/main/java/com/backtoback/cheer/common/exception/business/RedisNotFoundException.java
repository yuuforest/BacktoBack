package com.backtoback.cheer.common.exception.business;

import com.backtoback.cheer.common.exception.BusinessException;
import com.backtoback.cheer.common.exception.ErrorCode;

public class RedisNotFoundException extends BusinessException {

    public RedisNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RedisNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
