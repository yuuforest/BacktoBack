package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class PointLackException extends BusinessException {

    public PointLackException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PointLackException(ErrorCode errorCode) {
        super(errorCode);
    }

}
