package com.sluv.backoffice.domain.brand.service;

import com.sluv.backoffice.domain.brand.dto.HotBrandResDto;
import com.sluv.backoffice.domain.brand.repository.BrandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<HotBrandResDto> getTop3HotBrand() {
        return brandRepository.getTop3HotBrand();
    }
}
