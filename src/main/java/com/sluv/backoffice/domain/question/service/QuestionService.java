package com.sluv.backoffice.domain.question.service;

import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.domain.question.repository.QuestionReportRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionReportRepository questionReportRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<QuestionReportInfoDto> getAllQuestionReport(Pageable pageable, ReportStatus reportStatus) {
        Page<QuestionReportInfoDto> questionReport = questionReportRepository.getAllQuestionReport(pageable, reportStatus);

        return PaginationResDto.<QuestionReportInfoDto>builder()
                .hasNext(questionReport.hasNext())
                .page(questionReport.getNumber())
                .content(questionReport.getContent())
                .build();
    }
}
