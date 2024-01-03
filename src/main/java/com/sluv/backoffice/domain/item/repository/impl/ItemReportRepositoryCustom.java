package com.sluv.backoffice.domain.item.repository.impl;

import com.sluv.backoffice.domain.item.dto.ItemReportDetailDto;
import com.sluv.backoffice.domain.item.dto.ItemReportInfoDto;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemReportRepositoryCustom {

    Page<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus);

    ItemReportDetailDto getItemReportDetail(Long itemReportId);
}
