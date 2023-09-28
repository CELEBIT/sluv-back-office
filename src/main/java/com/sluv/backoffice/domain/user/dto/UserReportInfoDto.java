package com.sluv.backoffice.domain.user.dto;

import com.sluv.backoffice.domain.user.enums.UserReportReason;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReportInfoDto {

    private Long reporterId;
    private String reporterNickname;
    private Long reportedId;
    private String reportedNickname;
    private Long reportId;
    private UserReportReason reportReason;
    private String content;
    private ReportStatus reportStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
