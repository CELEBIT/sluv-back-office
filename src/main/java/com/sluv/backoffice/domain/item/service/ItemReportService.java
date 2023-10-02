package com.sluv.backoffice.domain.item.service;

import com.sluv.backoffice.domain.item.dto.ItemReportInfoDto;
import com.sluv.backoffice.domain.item.repository.ItemReportRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus) {
        Page<ItemReportInfoDto> itemReport = itemReportRepository.getAllItemReport(pageable, reportStatus);

        return PaginationResDto.<ItemReportInfoDto>builder()
                .hasNext(itemReport.hasNext())
                .page(itemReport.getNumber())
                .content(itemReport.getContent())
                .build();
    }
}
