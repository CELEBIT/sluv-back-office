package com.sluv.backoffice.domain.question.exception;

import org.springframework.http.HttpStatus;

public class QuestionReportNotFoundException extends QuestionException{

    private static final int ERROR_CODE = 2100;
    private static final String MESSAGE = "존재하지 않는 질문 게시글 신고입니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public QuestionReportNotFoundException() {
        super(ERROR_CODE,STATUS,MESSAGE);
    }
}