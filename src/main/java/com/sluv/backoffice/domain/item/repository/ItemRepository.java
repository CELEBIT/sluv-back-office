package com.sluv.backoffice.domain.item.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
