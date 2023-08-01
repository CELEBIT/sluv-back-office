package com.sluv.backoffice.domain.closet.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosetRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
