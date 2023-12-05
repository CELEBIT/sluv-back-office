package com.sluv.backoffice.domain.user.service;

import com.sluv.backoffice.domain.user.dto.UpdateUserReportResDto;
import com.sluv.backoffice.domain.user.dto.UserReportInfoDto;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReport;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
import com.sluv.backoffice.domain.user.exception.UserReportNotFoundException;
import com.sluv.backoffice.domain.user.repository.UserReportRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import com.sluv.backoffice.global.common.service.ReportProcessingService;
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
    private final ReportProcessingService reportProcessingService;

    @Transactional(readOnly = true)
    public PaginationResDto<UserReportInfoDto> getAllUserReport(Pageable pageable, ReportStatus reportStatus) {
        Page<UserReportInfoDto> reportInfoDtos = userReportRepository.getAllUserReport(pageable, reportStatus);

        return PaginationResDto.<UserReportInfoDto>builder()
                .hasNext(reportInfoDtos.hasNext())
                .page(reportInfoDtos.getNumber())
                .content(reportInfoDtos.getContent())
                .build();
    }

    public UpdateUserReportResDto updateUserReportStatus(Long userReportId, ReportStatus reportStatus) {
        if (reportStatus == ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        UserReport userReport = userReportRepository.findById(userReportId)
                .orElseThrow(UserReportNotFoundException::new);

        if (userReport.getReportStatus() != ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        User reportedUser = userReport.getReported();
        User reporterUser = userReport.getReporter();

        userReport.changeUserReportStatus(reportStatus);
        reportProcessingService.processReport(reportedUser, reporterUser, userReport.getContent(), reportStatus);

        return UpdateUserReportResDto.of(userReport.getReportStatus());
    }
}