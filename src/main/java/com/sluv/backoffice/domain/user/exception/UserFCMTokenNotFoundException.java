package com.sluv.backoffice.domain.user.exception;

import org.springframework.http.HttpStatus;

public class UserFCMTokenNotFoundException extends UserException {

    private static final int ERROR_CODE= 2002;
    private static final String MESSAGE= "유저의 fcm 토큰이 존재하지 않습니다.";
    private static final HttpStatus STATUS= HttpStatus.BAD_REQUEST;

    public UserFCMTokenNotFoundException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}
