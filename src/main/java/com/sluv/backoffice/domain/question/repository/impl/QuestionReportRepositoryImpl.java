package com.sluv.backoffice.domain.question.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.question.dto.QuestionReportDetailDto;
import com.sluv.backoffice.domain.question.dto.QuestionReportInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.sluv.backoffice.domain.question.entity.QQuestionReport.questionReport;
import static com.sluv.backoffice.domain.question.entity.QQuestion.question;

@RequiredArgsConstructor
public class QuestionReportRepositoryImpl implements QuestionReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser reporterUser = new QUser("reporterUser");
    private final QUser reportedUser = new QUser("reportedUser");

    @Override
    public Page<QuestionReportInfoDto> getAllQuestionReport(Pageable pageable, ReportStatus reportStatus) {
        BooleanExpression predicate = questionReport.isNotNull();
        if(reportStatus != null) {
            predicate = predicate.and(questionReport.reportStatus.eq(reportStatus));
        }

        List<QuestionReportInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(QuestionReportInfoDto.class,
                        questionReport.reporter.id,
                        questionReport.reporter.nickname,
                        question.user.id,
                        question.user.nickname,
                        questionReport.id,
                        questionReport.questionReportReason,
                        questionReport.content,
                        questionReport.reportStatus,
                        questionReport.createdAt,
                        questionReport.updatedAt))
                .from(questionReport)
                .where(predicate)
                .orderBy(questionReport.createdAt.desc())
                .join(questionReport.question, question)
                .join(questionReport.reporter, reporterUser)
                .join(question.user, reportedUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> jpaQueryFactory.from(questionReport).fetch().size());
    }

    @Override
    public Optional<QuestionReportDetailDto> getQuestionReportDetail(Long questionReportId) {
        QuestionReportDetailDto detailDto = jpaQueryFactory
                .select(Projections.constructor(QuestionReportDetailDto.class,
                        questionReport.reporter.id,
                        questionReport.reporter.nickname,
                        question.user.id,
                        question.user.nickname,
                        questionReport.id,
                        questionReport.questionReportReason,
                        questionReport.content,
                        questionReport.reportStatus,
                        question.title,
                        question.content,
                        questionReport.createdAt,
                        questionReport.updatedAt))
                .from(questionReport)
                .where(questionReport.id.eq(questionReportId))
                .join(questionReport.question,question)
                .join(questionReport.reporter, reporterUser)
                .join(question.user, reportedUser)
                .fetchOne();

        return Optional.ofNullable(detailDto);
    }
}
