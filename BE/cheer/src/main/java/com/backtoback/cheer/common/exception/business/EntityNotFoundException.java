package com.backtoback.cheer.common.exception.business;

import com.backtoback.cheer.common.exception.BusinessException;
import com.backtoback.cheer.common.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
