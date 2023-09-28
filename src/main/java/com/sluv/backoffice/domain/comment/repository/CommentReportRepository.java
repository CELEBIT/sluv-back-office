package com.sluv.backoffice.domain.comment.repository;

import com.sluv.backoffice.domain.comment.entity.CommentReport;
import com.sluv.backoffice.domain.comment.repository.impl.CommentReportRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long>, CommentReportRepositoryCustom {
}
