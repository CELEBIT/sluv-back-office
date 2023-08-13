package com.sluv.backoffice.domain.brand.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.brand.entity.Brand;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sluv.backoffice.domain.brand.entity.QBrand.brand;

@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Brand> test() {

        return jpaQueryFactory.selectFrom(brand)
                .fetch();
    }
}
