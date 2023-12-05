package com.sluv.backoffice.global.common.service;

import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReportStack;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.domain.user.repository.UserReportStackRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportProcessingService {

    private final UserReportStackRepository userReportStackRepository;
    private final FCMNotificationService fcmNotificationService;

    public void processReport(User reportedUser, User reporterUser, String content, ReportStatus status) {
        if (status == ReportStatus.COMPLETED) {
            userReportStackRepository.save(UserReportStack.toEntity(reportedUser));

            fcmNotificationService.sendFCMNotification(reportedUser.getId(), "당신은 신고 당했습니다.", content);
            fcmNotificationService.sendFCMNotification(reporterUser.getId(), "신고가 접수 되었습니다.", content);

            blockReportedUser(reportedUser);
        } else {
            fcmNotificationService.sendFCMNotification(reporterUser.getId(), "신고가 기각되었습니다.", content);
        }
    }

    private void blockReportedUser(User reportedUser) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long reportCount = userReportStackRepository.countByReportedAndCreatedAtAfter(reportedUser, oneMonthAgo);

        if (reportCount >= 3) {
            reportedUser.changeUserStatus(UserStatus.BLOCKED);
            fcmNotificationService.sendFCMNotification(reportedUser.getId(), "신고 누적으로 인한 계정정지 안내", "3회 이상 신고 누적으로 계정이 일시정지 됩니다.");
        }
    }
}
