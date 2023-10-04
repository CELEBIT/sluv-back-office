package com.sluv.backoffice.domain.question.repository;

import com.sluv.backoffice.domain.question.entity.QuestionReport;
import com.sluv.backoffice.domain.question.repository.impl.QuestionReportRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long>, QuestionReportRepositoryCustom {
}
