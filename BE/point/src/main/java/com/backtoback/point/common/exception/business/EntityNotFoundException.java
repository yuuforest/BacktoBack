package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
