package com.sluv.backoffice.domain.visitor.repository.impl;

import com.sluv.backoffice.domain.visitor.entity.VisitHistory;
import java.util.List;

public interface VisitHistoryRepositoryCustom {
    List<VisitHistory> getVisitHistoryFor10Days();
}
