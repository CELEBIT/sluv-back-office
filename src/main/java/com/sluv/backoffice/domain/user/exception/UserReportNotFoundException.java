package com.sluv.backoffice.domain.user.exception;

import org.springframework.http.HttpStatus;

public class UserReportNotFoundException extends UserException {
    private static final int ERROR_CODE = 2001;
    private static final String MESSAGE = "존재하지 않는 유저 신고입니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public UserReportNotFoundException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}