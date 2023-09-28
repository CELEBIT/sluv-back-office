package com.sluv.backoffice.domain.user.repository.impl;

import com.sluv.backoffice.domain.user.dto.UserReportInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReportRepositoryCustom {

    Page<UserReportInfoDto> getAllUserReport(Pageable pageable, ReportStatus reportStatus);
}
