package com.sluv.backoffice.domain.visit.repository;

import com.sluv.backoffice.domain.visit.entity.DailyVisit;
import com.sluv.backoffice.domain.visit.repository.impl.VisitHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitHistoryRepository extends JpaRepository<DailyVisit, Long>, VisitHistoryRepositoryCustom {
}
