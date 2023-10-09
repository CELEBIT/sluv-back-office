package com.sluv.backoffice.domain.comment.repository.impl;

import com.sluv.backoffice.domain.comment.dto.CommentReportDetailDto;
import com.sluv.backoffice.domain.comment.dto.CommentReportInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentReportRepositoryCustom {
    Page<CommentReportInfoDto> getAllCommentReport(Pageable pageable, ReportStatus reportStatus);

    CommentReportDetailDto getCommentReportDetail(Long commentReportId);
}
