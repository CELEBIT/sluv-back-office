package com.sluv.backoffice.global.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaginationResDto<T> {
    @Schema(description = "다음 페이지 존재 여부")
    private Boolean hasNext;
    @Schema(description = "현재 page")
    private Integer page;
    @Schema(description = "데이터들")
    private List<T> content;
}
