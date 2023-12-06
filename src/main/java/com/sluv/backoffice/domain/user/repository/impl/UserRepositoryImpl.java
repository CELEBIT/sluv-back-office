package com.sluv.backoffice.domain.user.repository.impl;

import static com.sluv.backoffice.domain.user.entity.QFollow.follow;
import static com.sluv.backoffice.domain.user.entity.QUser.user;
import static com.sluv.backoffice.domain.user.entity.QUserReport.userReport;
import static com.sluv.backoffice.domain.user.entity.QUserReportStack.userReportStack;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.user.dto.HotUserResDto;
import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

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

    @Override
    public List<HotUserResDto> getTop3HotUser() {
        List<Tuple> fetch = jpaQueryFactory.select(user, follow.count())
                .from(user)
                .leftJoin(follow).on(follow.followee.eq(user)).fetchJoin()
                .groupBy(user)
                .orderBy(follow.count().desc())
                .limit(3)
                .fetch();

        return fetch.stream()
                .map(tuple -> HotUserResDto.of(tuple.get(user), tuple.get(follow.count())))
                .toList();
    }
}
