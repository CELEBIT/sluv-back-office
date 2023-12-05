package com.sluv.backoffice.domain.brand.repository;

import static com.sluv.backoffice.domain.brand.entity.QBrand.brand;
import static com.sluv.backoffice.domain.item.entity.QItem.item;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.brand.dto.HotBrandResDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HotBrandResDto> getTop3HotBrand() {
        List<Tuple> fetch = jpaQueryFactory.select(brand, item.brand.count())
                .from(item)
                .groupBy(item.brand)
                .orderBy(item.brand.count().desc())
                .limit(3)
                .fetch();

        return fetch.stream()
                .map(tuple -> HotBrandResDto.of(tuple.get(brand), tuple.get(item.brand.count())))
                .toList();
    }
}
