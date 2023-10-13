package com.sluv.backoffice.domain.question.service;

import com.sluv.backoffice.domain.question.dto.QuestionReportDetailDto;
import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.domain.question.dto.UpdateQuestionReportResDto;
import com.sluv.backoffice.domain.question.entity.QuestionReport;
import com.sluv.backoffice.domain.question.enums.QuestionStatus;
import com.sluv.backoffice.domain.question.exception.QuestionReportNotFoundException;
import com.sluv.backoffice.domain.question.repository.QuestionReportRepository;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReportStack;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
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
public class QuestionReportService {

    private final QuestionReportRepository questionReportRepository;
    private final UserReportStackRepository userReportStackRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<QuestionReportInfoDto> getAllQuestionReport(Pageable pageable, ReportStatus reportStatus) {
        Page<QuestionReportInfoDto> questionReport = questionReportRepository.getAllQuestionReport(pageable, reportStatus);

        return PaginationResDto.<QuestionReportInfoDto>builder()
                .hasNext(questionReport.hasNext())
                .page(questionReport.getNumber())
                .content(questionReport.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public QuestionReportDetailDto getQuestionReportDetail(Long questionReportId) {
        return questionReportRepository.getQuestionReportDetail(questionReportId)
                .orElseThrow(QuestionReportNotFoundException::new);
    }

    public UpdateQuestionReportResDto updateQuestionReportStatus(Long questionReportId, ReportStatus reportStatus) {
        if (reportStatus == ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        QuestionReport questionReport = questionReportRepository.findById(questionReportId)
                .orElseThrow(QuestionReportNotFoundException::new);

        if (questionReport.getReportStatus() != ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        questionReport.changeQuestionReportStatus(reportStatus);

        if (reportStatus == ReportStatus.COMPLETED) {
            updateQuestionReportCompleted(questionReport);
        }
        return UpdateQuestionReportResDto.of(questionReport.getReportStatus());
    }

    /**
     *TODO:추후 신고자 및 피신고자 알림 설정 추가
     */
    private void updateQuestionReportCompleted(QuestionReport questionReport) {
        questionReport.getQuestion().changeQuestionStatus(QuestionStatus.BLOCKED);

        User reportedUser = questionReport.getQuestion().getUser();
        userReportStackRepository.save(UserReportStack.toEntity(reportedUser));

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long reportCount = userReportStackRepository.countByReportedAndCreatedAtAfter(reportedUser, oneMonthAgo);

        if (reportCount >= 3) {
            reportedUser.changeUserStatus(UserStatus.BLOCKED);
        }
    }
}
