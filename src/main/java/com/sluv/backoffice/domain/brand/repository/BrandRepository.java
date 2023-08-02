package com.sluv.backoffice.domain.brand.repository;

import com.sluv.backoffice.domain.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
