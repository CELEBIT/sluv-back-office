package com.sluv.backoffice.domain.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.domain.user.entity.QUserReport;
import com.sluv.backoffice.domain.user.entity.QUserReportStack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;
    private final QUserReport userReport = QUserReport.userReport;
    private final QUserReportStack userReportStack = QUserReportStack.userReportStack;

    @Override
    public Page<UserInfoDto> findAllUserInfo(Pageable pageable) {

        List<UserInfoDto> content = jpaQueryFactory
                .select(Projections.constructor(UserInfoDto.class,
                        user.nickname,
                        user.profileImgUrl,
                        user.userStatus,
                        JPAExpressions.select(userReport.reported.id.count())
                                .from(userReport)
                                .where(userReport.reported.id.eq(user.id)),
                        JPAExpressions.select(userReportStack.reported.id.count())
                                .from(userReportStack)
                                .where(userReportStack.reported.id.eq(user.id))
                ))
                .from(user)
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.from(user).fetch().size();

        return new PageImpl<>(content, pageable, total);
    }
}
