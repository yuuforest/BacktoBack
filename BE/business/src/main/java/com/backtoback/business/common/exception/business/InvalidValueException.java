package com.backtoback.business.common.exception.business;

import com.backtoback.business.common.exception.BusinessException;
import com.backtoback.business.common.exception.ErrorCode;

public class InvalidValueException extends BusinessException {

	public InvalidValueException(String value) {
		super(value, ErrorCode.INVALID_INPUT_VALUE);
	}

	public InvalidValueException(String value, ErrorCode errorCode) {
		super(value, errorCode);
	}

}