package com.sluv.backoffice.domain.item.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.item.dto.ItemReportInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sluv.backoffice.domain.item.entity.QItem.item;
import static com.sluv.backoffice.domain.item.entity.QItemReport.itemReport;

@RequiredArgsConstructor
public class ItemReportRepositoryImpl implements ItemReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus) {
        QUser reporterUser = new QUser("reporterUser");
        QUser reportedUser = new QUser("reportedUser");

        BooleanExpression predicate = itemReport.isNotNull();
        if(reportStatus != null) {
            predicate = predicate.and(itemReport.reportStatus.eq(reportStatus));
        }

        List<ItemReportInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(ItemReportInfoDto.class,
                        itemReport.reporter.id,
                        itemReport.reporter.nickname,
                        item.user.id,
                        item.user.nickname,
                        itemReport.id,
                        itemReport.itemReportReason,
                        itemReport.content,
                        itemReport.reportStatus,
                        itemReport.createdAt,
                        itemReport.updatedAt))
                .from(itemReport)
                .where(predicate)
                .orderBy(itemReport.createdAt.desc())
                .join(itemReport.item, item)
                .join(itemReport.reporter, reporterUser)
                .join(item.user, reportedUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> jpaQueryFactory.from(itemReport).fetch().size());
    }
}
