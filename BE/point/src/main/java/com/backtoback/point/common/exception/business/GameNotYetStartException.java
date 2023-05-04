package com.backtoback.point.common.exception.business;

import com.backtoback.point.common.exception.BusinessException;
import com.backtoback.point.common.exception.ErrorCode;

public class GameNotYetStartException extends BusinessException {

    public GameNotYetStartException(ErrorCode errorCode) {
        super(errorCode);
    }

}
