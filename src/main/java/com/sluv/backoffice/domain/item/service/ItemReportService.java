package com.sluv.backoffice.domain.item.service;

import com.sluv.backoffice.domain.item.dto.*;
import com.sluv.backoffice.domain.item.repository.*;
import com.sluv.backoffice.domain.item.entity.ItemReport;
import com.sluv.backoffice.domain.item.enums.ItemStatus;
import com.sluv.backoffice.domain.item.exception.ItemReportNotFoundException;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import com.sluv.backoffice.global.common.service.ReportProcessingService;
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
    private final ReportProcessingService reportProcessingService;

    @Transactional(readOnly = true)
    public PaginationResDto<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus) {
        Page<ItemReportInfoDto> itemReport = itemReportRepository.getAllItemReport(pageable, reportStatus);

        return PaginationResDto.<ItemReportInfoDto>builder()
                .hasNext(itemReport.hasNext())
                .page(itemReport.getNumber())
                .content(itemReport.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public ItemReportDetailDto getItemReportDetail(Long itemReportId) {

        return itemReportRepository.getItemReportDetail(itemReportId);

    }

    public UpdateItemReportResDto updateItemReportStatus(Long itemReportId, ReportStatus reportStatus) {
        if (reportStatus == ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        ItemReport itemReport = itemReportRepository.findById(itemReportId)
                .orElseThrow(ItemReportNotFoundException::new);

        if (itemReport.getReportStatus() != ReportStatus.WAITING) {
            throw new InvalidReportStatusException();
        }

        User reportedUser = itemReport.getItem().getUser();
        User reporterUser = itemReport.getReporter();

        itemReport.changeItemReportStatus(reportStatus);

        if (reportStatus == ReportStatus.COMPLETED) {
            itemReport.getItem().changeItemStatus(ItemStatus.BLOCKED);
        }
        reportProcessingService.processReport(reportedUser, reporterUser, itemReport.getContent(), reportStatus);

        return UpdateItemReportResDto.of(itemReport.getReportStatus());
    }
}
