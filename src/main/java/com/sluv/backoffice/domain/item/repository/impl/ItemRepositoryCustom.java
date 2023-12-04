package com.sluv.backoffice.domain.item.repository.impl;

import com.sluv.backoffice.domain.item.entity.Item;
import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> getAllItemWithCelebCategory();
}
