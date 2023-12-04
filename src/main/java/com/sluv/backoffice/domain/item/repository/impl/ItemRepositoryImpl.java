package com.sluv.backoffice.domain.item.repository.impl;

import static com.sluv.backoffice.domain.celeb.entity.QCeleb.celeb;
import static com.sluv.backoffice.domain.celeb.entity.QCelebCategory.celebCategory;
import static com.sluv.backoffice.domain.item.entity.QItem.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.item.entity.Item;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> getAllItemWithCelebCategory() {
        return jpaQueryFactory.selectFrom(item)
                .join(item.celeb, celeb).fetchJoin()
                .join(item.celeb.celebCategory, celebCategory).fetchJoin()
                .fetch();
    }

}
