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
import com.sluv.backoffice.global.common.service.FCMNotificationService;
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
    private final FCMNotificationService fcmNotificationService;

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
        updateUserReportAndNotify(userReport);

        return UpdateUserReportResDto.of(userReport.getReportStatus());
    }

    private void updateUserReportAndNotify(UserReport userReport) {
        User reportedUser = userReport.getReported();
        User reporterUser = userReport.getReporter();

        if (userReport.getReportStatus() == ReportStatus.COMPLETED) {
            userReportStackRepository.save(UserReportStack.toEntity(reportedUser));

            fcmNotificationService.sendFCMNotification(reportedUser.getId(), "당신은 신고 당했습니다.", userReport.getContent());
            fcmNotificationService.sendFCMNotification(reporterUser.getId(), "신고가 접수 되었습니다.", userReport.getContent());

            LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
            long reportCount = userReportStackRepository.countByReportedAndCreatedAtAfter(reportedUser, oneMonthAgo);

            if (reportCount >= 3) {
                reportedUser.changeUserStatus(UserStatus.BLOCKED);
                fcmNotificationService.sendFCMNotification(reportedUser.getId(), "신고 누적으로 인한 계정정지 안내", "3회 이상 신고 누적으로 계정이 일시정지 됩니다.");
            }
        }
        else {
            fcmNotificationService.sendFCMNotification(reporterUser.getId(), "신고가 기각되었습니다.", userReport.getContent());
        }
    }
}