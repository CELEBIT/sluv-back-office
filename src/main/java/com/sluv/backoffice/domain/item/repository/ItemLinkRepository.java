package com.sluv.backoffice.domain.item.repository;

import com.sluv.backoffice.domain.item.entity.ItemLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemLinkRepository extends JpaRepository<ItemLink, Long> {

    List<ItemLink> findAllByItemId(Long itemId);
}