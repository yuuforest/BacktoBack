package com.backtoback.cheer.common.exception.business;

import com.backtoback.cheer.common.exception.BusinessException;
import com.backtoback.cheer.common.exception.ErrorCode;

public class GameNotYetStartException extends BusinessException {

    public GameNotYetStartException(ErrorCode errorCode) {
        super(errorCode);
    }

}
