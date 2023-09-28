package com.sluv.backoffice.domain.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {
    private static final int ERROR_CODE= 2000;
    private static final String MESSAGE= "존재하지 않는 유저입니다.";
    private static final HttpStatus STATUS= HttpStatus.BAD_REQUEST;

    public UserNotFoundException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}

