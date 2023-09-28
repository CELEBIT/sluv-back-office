package com.sluv.backoffice.domain.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.user.dto.UserReportInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sluv.backoffice.domain.user.entity.QUserReport.userReport;

@RequiredArgsConstructor
public class UserReportRepositoryImpl implements UserReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserReportInfoDto> getAllUserReport(Pageable pageable, ReportStatus reportStatus) {
        QUser reporterUser = new QUser("reporterUser");
        QUser reportedUser = new QUser("reportedUser");

        BooleanExpression predicate = userReport.isNotNull();
        if(reportStatus != null) {
            predicate = predicate.and(userReport.reportStatus.eq(reportStatus));
        }

        List<UserReportInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(UserReportInfoDto.class,
                        userReport.reporter.id,
                        userReport.reporter.nickname,
                        userReport.reported.id,
                        userReport.reported.nickname,
                        userReport.id,
                        userReport.userReportReason,
                        userReport.content,
                        userReport.reportStatus,
                        userReport.createdAt,
                        userReport.updatedAt))
                .from(userReport)
                .where(predicate)
                .orderBy(userReport.createdAt.desc())
                .join(userReport.reporter, reporterUser)
                .join(userReport.reported, reportedUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> jpaQueryFactory.from(userReport).fetch().size());
    }
}
