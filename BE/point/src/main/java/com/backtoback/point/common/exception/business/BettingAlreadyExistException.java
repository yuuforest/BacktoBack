package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class BettingAlreadyExistException extends BusinessException {

    public BettingAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }

}
