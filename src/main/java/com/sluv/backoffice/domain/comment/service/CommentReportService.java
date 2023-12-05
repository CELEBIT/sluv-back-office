package com.sluv.backoffice.domain.comment.service;

import com.sluv.backoffice.domain.comment.dto.CommentReportDetailDto;
import com.sluv.backoffice.domain.comment.dto.CommentReportInfoDto;
import com.sluv.backoffice.domain.comment.dto.UpdateCommentReportResDto;
import com.sluv.backoffice.domain.comment.entity.CommentReport;
import com.sluv.backoffice.domain.comment.enums.CommentStatus;
import com.sluv.backoffice.domain.comment.exception.CommentReportNotFoundException;
import com.sluv.backoffice.domain.comment.repository.CommentReportRepository;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
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
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final ReportProcessingService reportProcessingService;

    @Transactional(readOnly = true)
    public PaginationResDto<CommentReportInfoDto> getAllCommentReport(Pageable pageable, ReportStatus reportStatus) {
        Page<CommentReportInfoDto> commentReport = commentReportRepository.getAllCommentReport(pageable, reportStatus);

        return PaginationResDto.<CommentReportInfoDto>builder()
                .hasNext(commentReport.hasNext())
                .page(commentReport.getNumber())
                .content(commentReport.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public CommentReportDetailDto getCommentReportDetail(Long commentReportId) {
        return commentReportRepository.getCommentReportDetail(commentReportId)
                .orElseThrow(CommentReportNotFoundException::new);
    }

    public UpdateCommentReportResDto updateCommentReportStatus(Long commentReportId, ReportStatus reportStatus) {
        if (reportStatus == ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        CommentReport commentReport = commentReportRepository.findById(commentReportId)
                .orElseThrow(CommentReportNotFoundException::new);

        if (commentReport.getReportStatus() != ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        User reportedUser = commentReport.getComment().getUser();
        User reporterUser = commentReport.getReporter();

        commentReport.changeCommentReportStatus(reportStatus);

        if (reportStatus == ReportStatus.COMPLETED) {
            commentReport.getComment().changeCommentStatus(CommentStatus.BLOCKED);
        }
        reportProcessingService.processReport(reportedUser, reporterUser, commentReport.getContent(), reportStatus);

        return UpdateCommentReportResDto.of(commentReport.getReportStatus());
    }
}

