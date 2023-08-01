package com.sluv.backoffice.domain.comment.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
