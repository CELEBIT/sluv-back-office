package com.sluv.backoffice.domain.user.dto;

import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReportResDto {

    private ReportStatus reportStatus;

    public static UpdateUserReportResDto of(ReportStatus reportStatus) {
        return new UpdateUserReportResDto(
                reportStatus
        );
    }
}
