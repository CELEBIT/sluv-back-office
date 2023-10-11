package com.sluv.backoffice.domain.user.service;

import com.sluv.backoffice.domain.user.dto.UpdateUserReportResDto;
import com.sluv.backoffice.domain.user.dto.UserReportInfoDto;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReport;
import com.sluv.backoffice.domain.user.entity.UserReportStack;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
import com.sluv.backoffice.domain.user.exception.UserReportNotFoundException;
import com.sluv.backoffice.domain.user.repository.UserReportRepository;
import com.sluv.backoffice.domain.user.repository.UserReportStackRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserReportService {

    private final UserReportRepository userReportRepository;
    private final UserReportStackRepository userReportStackRepository;

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

        userReport.changeUserReportStatus(reportStatus);

        if (reportStatus == ReportStatus.COMPLETED) {
            updateUserReportCompleted(userReport);
        }
        return UpdateUserReportResDto.of(userReport.getReportStatus());
    }

    /**
     *  TODO: 추후 신고자 및 피신고자 알림 설정 추가
     */
    private void updateUserReportCompleted(UserReport userReport) {
        User reportedUser = userReport.getReported();

        UserReportStack reportStack = UserReportStack.builder()
                .reported(reportedUser)
                .build();
        userReportStackRepository.save(reportStack);

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long reportCount = userReportStackRepository.countByReportedAndCreatedAtAfter(reportedUser, oneMonthAgo);

        if (reportCount >= 3) {
            reportedUser.changeUserStatus(UserStatus.BLOCKED);
        }
    }
}
