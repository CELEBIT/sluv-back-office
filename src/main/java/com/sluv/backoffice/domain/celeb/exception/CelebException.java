package com.sluv.backoffice.domain.celeb.exception;

import com.sluv.backoffice.global.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class CelebException extends ApplicationException {
    public CelebException(int errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
