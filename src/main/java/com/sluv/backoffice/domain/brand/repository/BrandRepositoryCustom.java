package com.sluv.backoffice.domain.brand.repository;

import com.sluv.backoffice.domain.brand.dto.HotBrandResDto;
import java.util.List;

public interface BrandRepositoryCustom {
    List<HotBrandResDto> getTop3HotBrand();
}
