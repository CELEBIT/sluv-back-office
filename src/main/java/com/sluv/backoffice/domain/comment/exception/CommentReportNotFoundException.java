package com.sluv.backoffice.domain.comment.exception;

import org.springframework.http.HttpStatus;

public class CommentReportNotFoundException extends CommentException {

    private static final int ERROR_CODE = 2200;
    private static final String MESSAGE = "존재하지 않는 댓글 신고입니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CommentReportNotFoundException() {
            super(ERROR_CODE,STATUS,MESSAGE);
        }
}
