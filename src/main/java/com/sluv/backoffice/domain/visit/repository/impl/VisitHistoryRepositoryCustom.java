package com.sluv.backoffice.domain.visit.repository.impl;

import com.sluv.backoffice.domain.visit.entity.VisitHistory;
import java.util.List;

public interface VisitHistoryRepositoryCustom {
    List<VisitHistory> getVisitHistoryFor10Days();
}
