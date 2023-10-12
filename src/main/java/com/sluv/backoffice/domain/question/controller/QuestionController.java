package com.sluv.backoffice.domain.question.controller;

import com.sluv.backoffice.domain.question.dto.QuestionReportDetailDto;
import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.domain.question.dto.UpdateQuestionReportResDto;
import com.sluv.backoffice.domain.question.service.QuestionReportService;
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
@RequestMapping("/backoffice/question")
public class QuestionController {

    private final QuestionReportService questionReportService;

    @Operation(
            summary = "질문 신고 정보 조히",
            description = "WAITING, COMPLETED, REJECTED 로 검색 조건, 없으면 전체 검색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/report")
    public ResponseEntity<SuccessDataResponse<PaginationResDto<QuestionReportInfoDto>>> getAllQuestionReport(Pageable pageable,
                                                                                                             @RequestParam(required = false) ReportStatus reportStatus) {
        return ResponseEntity.ok().body(SuccessDataResponse.<PaginationResDto<QuestionReportInfoDto>>builder()
                .result(questionReportService.getAllQuestionReport(pageable, reportStatus))
                .build());
    }

    @Operation(
            summary = "질문 신고 상세 정보 조히",
            description = "질문 신고 id를 통해 질문 신고 상세 정보 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/report/{questionReportId}")
    public ResponseEntity<SuccessDataResponse<QuestionReportDetailDto>> getQuestionReportDetail(@PathVariable Long questionReportId) {
        return ResponseEntity.ok().body(SuccessDataResponse.<QuestionReportDetailDto>builder()
                .result(questionReportService.getQuestionReportDetail(questionReportId))
                .build());
    }

    @Operation(
            summary = "질문 신고 처리",
            description = "질문 신고 id와 reportStatus(COMPLETED, REJECTED)를 통해 질문 신고 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/report/{questionReportId}")
    public ResponseEntity<SuccessDataResponse<UpdateQuestionReportResDto>> changeQuestionReportStatus(@PathVariable Long questionReportId,
                                                                                                      @RequestParam ReportStatus reportStatus) {

        return ResponseEntity.ok().body(SuccessDataResponse.<UpdateQuestionReportResDto>builder()
                .result(questionReportService.updateQuestionReportStatus(questionReportId, reportStatus))
                .build());
    }
}
