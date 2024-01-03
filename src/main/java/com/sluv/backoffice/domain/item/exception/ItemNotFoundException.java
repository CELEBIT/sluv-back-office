package com.sluv.backoffice.domain.item.exception;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends ItemException {

    private static final int ERROR_CODE = 2301;
    private static final String MESSAGE = "존재하지 않는 아이템입니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public ItemNotFoundException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}
