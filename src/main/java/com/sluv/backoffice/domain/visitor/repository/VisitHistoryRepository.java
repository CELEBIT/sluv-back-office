package com.sluv.backoffice.domain.visitor.repository;

import com.sluv.backoffice.domain.visitor.entity.DailyVisit;
import com.sluv.backoffice.domain.visitor.repository.impl.VisitHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitHistoryRepository extends JpaRepository<DailyVisit, Long>, VisitHistoryRepositoryCustom {
}
