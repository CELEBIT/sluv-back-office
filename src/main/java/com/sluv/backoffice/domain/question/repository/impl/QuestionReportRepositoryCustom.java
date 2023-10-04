package com.sluv.backoffice.domain.question.repository.impl;

import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReportRepositoryCustom {

    Page<QuestionReportInfoDto> getAllQuestionReport(Pageable pageable, ReportStatus reportStatus);
}
