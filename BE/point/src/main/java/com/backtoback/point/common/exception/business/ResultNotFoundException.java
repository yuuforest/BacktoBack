package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class ResultNotFoundException extends BusinessException {

    public ResultNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
