package com.sluv.backoffice.domain.item.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.item.dto.*;
import com.sluv.backoffice.domain.item.entity.Item;
import com.sluv.backoffice.domain.item.exception.ItemNotFoundException;
import com.sluv.backoffice.domain.item.repository.ItemImgRepository;
import com.sluv.backoffice.domain.item.repository.ItemLinkRepository;
import com.sluv.backoffice.domain.user.entity.QUser;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.sluv.backoffice.domain.item.entity.QItem.item;
import static com.sluv.backoffice.domain.item.entity.QItemReport.itemReport;

@RequiredArgsConstructor
public class ItemReportRepositoryImpl implements ItemReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final ItemImgRepository itemImgRepository;
    private final ItemLinkRepository itemLinkRepository;
    private final QUser reporterUser = new QUser("reporterUser");
    private final QUser reportedUser = new QUser("reportedUser");

    @Override
    public Page<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus) {
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

    @Override
    public ItemReportDetailDto getItemReportDetail(Long itemReportId) {

        Optional<Item> optionalItem = Optional.ofNullable(
                jpaQueryFactory
                        .select(item)
                        .from(itemReport)
                        .join(itemReport.item, item)
                        .where(itemReport.id.eq(itemReportId))
                        .fetchOne()
        );
        Item itemEntity = optionalItem.orElseThrow(ItemNotFoundException::new);

        List<ItemImgResDto> imgList = itemImgRepository.findAllByItemId(itemEntity.getId())
                .stream()
                .map(ItemImgResDto::of)
                .toList();

        List<ItemLinkResDto> linkList = itemLinkRepository.findAllByItemId(itemEntity.getId())
                .stream()
                .map(ItemLinkResDto::of)
                .toList();

        ItemReportDetailDto detailDto = jpaQueryFactory
                .select(Projections.constructor(ItemReportDetailDto.class,
                        itemReport.reporter.id,
                        itemReport.reporter.nickname,
                        item.user.id,
                        item.user.nickname,
                        itemReport.id,
                        itemReport.itemReportReason,
                        itemReport.content,
                        itemReport.reportStatus,
                        Expressions.constant(imgList),
                        Expressions.constant(linkList),
                        Expressions.constant(CelebSearchResDto.of(itemEntity.getCeleb())),
                        Expressions.constant(BrandSearchResDto.of(itemEntity.getBrand())),
                        Expressions.constant(ItemCategoryDto.of(itemEntity.getCategory())),
                        item.additionalInfo,
                        item.color,
                        item.name,
                        item.price,
                        item.whenDiscovery,
                        item.whereDiscovery,
                        itemReport.createdAt,
                        itemReport.updatedAt
                ))
                .from(itemReport)
                .join(itemReport.item, item)
                .join(itemReport.reporter, reporterUser)
                .join(itemReport.item.user, reportedUser)
                .where(itemReport.id.eq(itemReportId))
                .fetchOne();

        return detailDto;
    }
}