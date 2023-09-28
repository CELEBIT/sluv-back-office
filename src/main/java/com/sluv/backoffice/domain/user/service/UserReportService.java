package com.sluv.backoffice.domain.user.service;

import com.sluv.backoffice.domain.user.dto.UserReportInfoDto;
import com.sluv.backoffice.domain.user.repository.UserReportRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserReportService {

    private final UserReportRepository userReportRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<UserReportInfoDto> getAllUserReport(Pageable pageable, ReportStatus reportStatus) {
        Page<UserReportInfoDto> reportInfoDtos = userReportRepository.getAllUserReport(pageable, reportStatus);

        return PaginationResDto.<UserReportInfoDto>builder()
                .hasNext(reportInfoDtos.hasNext())
                .page(reportInfoDtos.getNumber())
                .content(reportInfoDtos.getContent())
                .build();
    }
}
