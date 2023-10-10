package com.sluv.backoffice.domain.question.dto;

import com.sluv.backoffice.domain.question.enums.QuestionReportReason;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReportDetailDto {

    private Long reporterId;
    private String reporterNickname;
    private Long reportedId;
    private String reportedNickname;
    private Long reportId;
    @Schema(description = "질문 신고 이유(enums)")
    private QuestionReportReason reportReason;
    @Schema(description = "신고 상세 내용")
    private String content;
    @Schema(description = "신고 접수 상태")
    private ReportStatus reportStatus;
    @Schema(description = "신고된 질문 제목")
    private String reportedQuestionTitle;
    @Schema(description = "신고된 질문 본문")
    private String reportedQuestionContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}