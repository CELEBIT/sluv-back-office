package com.sluv.backoffice.domain.item.repository;

import com.sluv.backoffice.domain.item.entity.Item;
import com.sluv.backoffice.domain.item.repository.impl.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
