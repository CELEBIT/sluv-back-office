package com.sluv.backoffice.domain.user.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.domain.user.entity.QUserReport;
import com.sluv.backoffice.domain.user.entity.QUserReportStack;
import com.sluv.backoffice.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;
    private final QUserReport userReport = QUserReport.userReport;
    private final QUserReportStack userReportStack = QUserReportStack.userReportStack;

    @Override
    public Page<UserInfoDto> findAllUserInfo(Pageable pageable) {

        List<Tuple> tuples = jpaQueryFactory
                .select(user, userReport.count(), userReportStack.countDistinct())
                .from(user)
                .leftJoin(userReport).on(user.eq(userReport.reported))
                .leftJoin(userReportStack).on(user.eq(userReportStack.reported))
                .groupBy(user)
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<UserInfoDto> userInfoDtos = tuples.stream()
                .map(tuple -> {
                    User u = tuple.get(user);
                    Long reportCount = tuple.get(userReport.count());
                    Long reportStackCount = tuple.get(userReportStack.countDistinct());
                    return UserInfoDto.of(u, reportCount, reportStackCount);
                })
                .collect(Collectors.toList());

        long totalCount = jpaQueryFactory
                .selectFrom(user)
                .leftJoin(userReport).on(user.eq(userReport.reported))
                .leftJoin(userReportStack).on(user.eq(userReportStack.reported))
                .groupBy(user.id)
                .fetch().size();

        return new PageImpl<>(userInfoDtos, pageable, totalCount);
    }
}
