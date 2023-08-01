package com.sluv.backoffice.domain.user.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
