package com.sluv.backoffice.domain.question.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
