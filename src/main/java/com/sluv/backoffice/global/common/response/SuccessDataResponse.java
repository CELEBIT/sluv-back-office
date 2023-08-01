package com.sluv.backoffice.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  성공 결과를 포함한 성공 Response
 */

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class SuccessDataResponse<T> extends SuccessResponse {
    private T result;

    @Builder
    public SuccessDataResponse(T result) {
        this.result = result;
    }

}
