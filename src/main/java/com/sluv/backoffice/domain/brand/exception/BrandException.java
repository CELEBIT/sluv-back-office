package com.sluv.backoffice.domain.brand.exception;

import com.sluv.backoffice.global.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class BrandException extends ApplicationException {
    public BrandException(int errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
