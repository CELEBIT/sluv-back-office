package com.sluv.backoffice.domain.item.repository;

import com.sluv.backoffice.domain.item.entity.ItemReport;
import com.sluv.backoffice.domain.item.repository.impl.ItemReportRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReportRepository extends JpaRepository<ItemReport, Long>, ItemReportRepositoryCustom {
}
