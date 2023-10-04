package com.sluv.backoffice.domain.comment.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.comment.dto.CommentReportInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sluv.backoffice.domain.comment.entity.QComment.comment;
import static com.sluv.backoffice.domain.comment.entity.QCommentReport.commentReport;

@RequiredArgsConstructor
public class CommentReportRepositoryImpl implements CommentReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentReportInfoDto> getAllCommentReport(Pageable pageable, ReportStatus reportStatus) {
        QUser reporterUser = new QUser("reporterUser");
        QUser reportedUser = new QUser("reportedUser");

        BooleanExpression predicate = commentReport.isNotNull();
        if(reportStatus != null) {
            predicate = predicate.and(commentReport.reportStatus.eq(reportStatus));
        }

        List<CommentReportInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(CommentReportInfoDto.class,
                        commentReport.reporter.id,
                        commentReport.reporter.nickname,
                        comment.user.id,
                        comment.user.nickname,
                        commentReport.id,
                        commentReport.commentReportReason,
                        commentReport.content,
                        commentReport.reportStatus,
                        commentReport.createdAt,
                        commentReport.updatedAt))
                .from(commentReport)
                .where(predicate)
                .orderBy(commentReport.createdAt.desc())
                .join(commentReport.comment,comment)
                .join(commentReport.reporter, reporterUser)
                .join(comment.user, reportedUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> jpaQueryFactory.from(commentReport).fetch().size());
    }
}
