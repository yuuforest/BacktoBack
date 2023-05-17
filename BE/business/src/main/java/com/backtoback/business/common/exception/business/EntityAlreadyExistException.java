package com.backtoback.business.common.exception.business;

import com.backtoback.business.common.exception.BusinessException;
import com.backtoback.business.common.exception.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

	public EntityAlreadyExistException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

}