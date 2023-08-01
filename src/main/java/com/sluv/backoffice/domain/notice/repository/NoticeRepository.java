package com.sluv.backoffice.domain.notice.repository;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<KafkaProperties.Admin, Long> {
}
