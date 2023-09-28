package com.sluv.backoffice.domain.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sluv.backoffice.domain.user.entity.QUser.*;
import static com.sluv.backoffice.domain.user.entity.QUserReport.*;
import static com.sluv.backoffice.domain.user.entity.QUserReportStack.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserInfoDto> getAllUserInfo(Pageable pageable) {

        List<UserInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(UserInfoDto.class,
                        user.nickname,
                        user.profileImgUrl,
                        user.userStatus,
                        JPAExpressions.select(userReport.reported.id.count())
                                .from(userReport)
                                .where(userReport.reported.id.eq(user.id)
                                .and(userReport.reportStatus.stringValue().eq(ReportStatus.WAITING.name()))),
                        JPAExpressions.select(userReportStack.reported.id.count())
                                .from(userReportStack)
                                .where(userReportStack.reported.id.eq(user.id))
                ))
                .from(user)
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> jpaQueryFactory.from(user).fetch().size());
    }
}
