package com.backtoback.business.common.exception.business;

import com.backtoback.business.common.exception.BusinessException;
import com.backtoback.business.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(String message) {
		super(message, ErrorCode.USER_NOT_FOUND);
	}

	public UserNotFoundException(ErrorCode errorCode) {
		super(ErrorCode.USER_NOT_FOUND);
	}

}