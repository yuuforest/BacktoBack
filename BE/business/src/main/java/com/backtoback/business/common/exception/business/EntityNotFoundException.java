package com.backtoback.business.common.exception.business;

import com.backtoback.business.common.exception.BusinessException;
import com.backtoback.business.common.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

}