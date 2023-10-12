package com.sluv.backoffice.domain.comment.controller;

import com.sluv.backoffice.domain.comment.dto.CommentReportDetailDto;
import com.sluv.backoffice.domain.comment.dto.CommentReportInfoDto;
import com.sluv.backoffice.domain.comment.dto.UpdateCommentReportResDto;
import com.sluv.backoffice.domain.comment.service.CommentReportService;
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
@RequestMapping("/backoffice/comment")
public class CommentController {

    private final CommentReportService commentReportService;

    @Operation(
            summary = "댓글 신고 정보 조히",
            description = "WAITING, COMPLETED, REJECTED 로 검색 조건, 없으면 전체 검색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/report")
    public ResponseEntity<SuccessDataResponse<PaginationResDto<CommentReportInfoDto>>> getAllCommentReport(Pageable pageable,
                                                                                                           @RequestParam(required = false) ReportStatus reportStatus) {
        return ResponseEntity.ok().body(SuccessDataResponse.<PaginationResDto<CommentReportInfoDto>>builder()
                .result(commentReportService.getAllCommentReport(pageable, reportStatus))
                .build());
    }

    @Operation(
            summary = "댓글 신고 상세 정보 조히",
            description = "댓글 신고 id를 통해 댓글 신고 상세 정보 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/report/{commentReportId}")
    public ResponseEntity<SuccessDataResponse<CommentReportDetailDto>> getCommentReportDetail(@PathVariable Long commentReportId) {
        return ResponseEntity.ok().body(SuccessDataResponse.<CommentReportDetailDto>builder()
                .result(commentReportService.getCommentReportDetail(commentReportId))
                .build());
    }

    @Operation(
            summary = "댓글 신고 처리",
            description = "댓글 신고 id와 reportStatus(COMPLETED, REJECTED)를 통해 댓글 신고 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/report/{commentReportId}")
    public ResponseEntity<SuccessDataResponse<UpdateCommentReportResDto>> changeCommentReportStatus(@PathVariable Long commentReportId,
                                                                                                    @RequestParam ReportStatus reportStatus) {

        return ResponseEntity.ok().body(SuccessDataResponse.<UpdateCommentReportResDto>builder()
                .result(commentReportService.updateCommentReportStatus(commentReportId, reportStatus))
                .build());
    }
}
