package com.sluv.backoffice.domain.celeb.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
