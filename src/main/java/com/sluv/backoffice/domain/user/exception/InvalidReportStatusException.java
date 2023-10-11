package com.sluv.backoffice.domain.user.exception;

import com.sluv.backoffice.global.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidReportStatusException extends ApplicationException {

    private static final int ERROR_CODE = 2002;
    private static final String MESSAGE = "잘못된 UserReportStatus 입니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public InvalidReportStatusException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}