package com.sluv.backoffice.domain.item.repository.impl;

import static com.sluv.backoffice.domain.celeb.entity.QCeleb.celeb;
import static com.sluv.backoffice.domain.celeb.entity.QCelebCategory.celebCategory;
import static com.sluv.backoffice.domain.item.entity.QItem.item;
import static com.sluv.backoffice.domain.item.entity.QItemImg.itemImg;
import static com.sluv.backoffice.domain.item.entity.QItemLike.itemLike;
import static com.sluv.backoffice.domain.item.entity.QItemScrap.itemScrap;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.item.dto.HotItemResDto;
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

    @Override
    public List<HotItemResDto> getTop3HotItem() {
        List<Tuple> fetch = jpaQueryFactory.select(item, itemImg, itemLike.count(), itemScrap.count())
                .from(item)
                .leftJoin(itemImg).on(itemImg.item.eq(item)).fetchJoin()
                .leftJoin(itemLike).on(itemLike.item.eq(item)).fetchJoin()
                .leftJoin(itemScrap).on(itemScrap.item.eq(item)).fetchJoin()
                .where(itemImg.representFlag.eq(true))
                .groupBy(item, itemLike, itemScrap)
                .orderBy(item.viewNum.add(itemLike.count()).add(itemScrap.count()).desc(),
                        item.viewNum.desc(),
                        itemLike.count().desc(),
                        itemScrap.count().desc()
                )
                .limit(3)
                .fetch();

        return fetch.stream()
                .map(tuple -> HotItemResDto.of(tuple.get(item), tuple.get(itemImg).getItemImgUrl(),
                        tuple.get(itemLike.count()), tuple.get(itemScrap.count())))
                .toList();
    }
}
