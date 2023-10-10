package com.sluv.backoffice.domain.question.repository.impl;

import com.sluv.backoffice.domain.question.dto.QuestionReportDetailDto;
import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionReportRepositoryCustom {

    Page<QuestionReportInfoDto> getAllQuestionReport(Pageable pageable, ReportStatus reportStatus);

    Optional<QuestionReportDetailDto> getQuestionReportDetail(Long questionReportId);
}
