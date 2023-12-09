package com.sluv.backoffice.domain.item.repository;

import com.sluv.backoffice.domain.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
}