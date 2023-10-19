package com.sluv.backoffice.domain.item.controller;

import com.sluv.backoffice.domain.item.dto.ItemReportInfoDto;
import com.sluv.backoffice.domain.item.dto.UpdateItemReportResDto;
import com.sluv.backoffice.domain.item.service.ItemReportService;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.ErrorResponse;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import com.sluv.backoffice.global.common.response.SuccessDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backoffice/item")
public class ItemController {

    private final ItemReportService itemReportService;

    @Operation(
            summary = "아이템 신고 정보 조히",
            description = "WAITING, COMPLETED, REJECTED 로 검색 조건, 없으면 전체 검색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/report")
    public ResponseEntity<SuccessDataResponse<PaginationResDto<ItemReportInfoDto>>> getAllItemReport(Pageable pageable,
                                                                                                     @RequestParam(required = false) ReportStatus reportStatus) {
        return ResponseEntity.ok().body(SuccessDataResponse.<PaginationResDto<ItemReportInfoDto>>builder()
                .result(itemReportService.getAllItemReport(pageable, reportStatus))
                .build());
    }

    @Operation(
            summary = "아이템 신고 처리",
            description = "아이템 신고 id와 reportStatus(COMPLETED, REJECTED)를 통해 질문 신고 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/report/{itemReportId}")
    public ResponseEntity<SuccessDataResponse<UpdateItemReportResDto>> changeItemReportStatus(@PathVariable Long itemReportId,
                                                                                              @RequestParam ReportStatus reportStatus) {

        return ResponseEntity.ok().body(SuccessDataResponse.<UpdateItemReportResDto>builder()
                .result(itemReportService.updateItemReportStatus(itemReportId, reportStatus))
                .build());
    }
}
