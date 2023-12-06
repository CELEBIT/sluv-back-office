package com.sluv.backoffice.domain.item.repository.impl;

import com.sluv.backoffice.domain.item.dto.HotItemResDto;
import com.sluv.backoffice.domain.item.entity.Item;
import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> getAllItemWithCelebCategory();

    List<HotItemResDto> getTop3HotItem();
}
