package com.sluv.backoffice.domain.notice.exception;

import com.sluv.backoffice.global.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class NoticeException extends ApplicationException {
    public NoticeException(int errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
