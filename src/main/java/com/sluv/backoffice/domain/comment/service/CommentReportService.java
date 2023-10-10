package com.sluv.backoffice.domain.comment.service;

import com.sluv.backoffice.domain.comment.dto.CommentReportDetailDto;
import com.sluv.backoffice.domain.comment.dto.CommentReportInfoDto;
import com.sluv.backoffice.domain.comment.exception.CommentReportNotFoundException;
import com.sluv.backoffice.domain.comment.repository.CommentReportRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;

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
}

