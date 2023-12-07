package com.sluv.backoffice.domain.visitor.repository;

import com.sluv.backoffice.domain.visitor.entity.DailyVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyVisitRepository extends JpaRepository<DailyVisit, Long> {
}
