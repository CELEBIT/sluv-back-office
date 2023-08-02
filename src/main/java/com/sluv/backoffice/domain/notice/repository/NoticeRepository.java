package com.sluv.backoffice.domain.notice.repository;

import com.sluv.backoffice.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
