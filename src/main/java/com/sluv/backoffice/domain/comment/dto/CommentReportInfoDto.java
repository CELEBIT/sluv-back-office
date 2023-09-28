package com.sluv.backoffice.domain.comment.dto;

import com.sluv.backoffice.domain.comment.enums.CommentReportReason;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReportInfoDto {

    private Long reporterId;
    private String reporterNickname;
    private Long reportedId;
    private String reportedNickname;
    private Long reportId;
    @Schema(description = "댓글 신고 이유(enums)")
    private CommentReportReason reportReason;
    @Schema(description = "신고 상세 내용")
    private String content;
    @Schema(description = "신고 접수 상태")
    private ReportStatus reportStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
