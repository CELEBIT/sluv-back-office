package com.sluv.backoffice.domain.item.dto;

import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemReportResDto {

    private ReportStatus reportStatus;

    public static UpdateItemReportResDto of(ReportStatus reportStatus) {
        return new UpdateItemReportResDto(
                reportStatus
        );
    }
}
