package com.sluv.backoffice.domain.item.repository;

import com.sluv.backoffice.domain.item.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findAllByItemId(Long itemId);
}