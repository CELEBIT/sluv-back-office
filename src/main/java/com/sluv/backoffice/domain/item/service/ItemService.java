package com.sluv.backoffice.domain.item.service;

import com.sluv.backoffice.domain.celeb.entity.Celeb;
import com.sluv.backoffice.domain.celeb.entity.CelebCategory;
import com.sluv.backoffice.domain.item.entity.Item;
import com.sluv.backoffice.domain.item.repository.ItemRepository;
import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public UserCountByCategoryResDto getItemCountByCelebCategoryParent() {
        List<Item> allItem = itemRepository.getAllItemWithCelebCategory();

        HashMap<String, Long> collect = allItem.stream().map(Item::getCeleb)
                .map(Celeb::getCelebCategory)
                .map(celebCategory -> celebCategory.getParent() == null ? celebCategory : celebCategory.getParent())
                .collect(Collectors.groupingBy(CelebCategory::getName, HashMap::new, Collectors.counting()));

        return UserCountByCategoryResDto.of(collect, allItem.stream().count());
    }

    public UserCountByCategoryResDto getItemCountByCelebCategoryChild(String parentCategory) {
        List<Item> allItem = itemRepository.getAllItemWithCelebCategory();

        HashMap<String, Long> collect = allItem.stream().map(Item::getCeleb)
                .map(Celeb::getCelebCategory)
                .filter(celebCategory -> celebCategory.getParent() != null)
                .filter(celebCategory -> celebCategory.getParent().getName().equals(parentCategory))
                .collect(Collectors.groupingBy(CelebCategory::getName, HashMap::new, Collectors.counting()));

        return UserCountByCategoryResDto.of(collect, allItem.stream().count());
    }
}
