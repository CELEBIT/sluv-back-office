package com.sluv.backoffice.domain.brand.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
