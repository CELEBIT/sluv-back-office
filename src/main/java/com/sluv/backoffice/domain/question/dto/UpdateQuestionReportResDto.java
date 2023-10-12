package com.sluv.backoffice.domain.question.dto;

import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionReportResDto {

    private ReportStatus reportStatus;

    public static UpdateQuestionReportResDto of(ReportStatus reportStatus) {
        return new UpdateQuestionReportResDto(
                reportStatus
        );
    }
}