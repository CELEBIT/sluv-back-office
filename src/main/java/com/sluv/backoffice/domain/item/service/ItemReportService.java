package com.sluv.backoffice.domain.item.service;

import com.sluv.backoffice.domain.item.dto.*;
import com.sluv.backoffice.domain.item.entity.Item;
import com.sluv.backoffice.domain.item.repository.*;
import com.sluv.backoffice.domain.item.entity.ItemReport;
import com.sluv.backoffice.domain.item.enums.ItemStatus;
import com.sluv.backoffice.domain.item.exception.ItemNotFoundException;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;
    private final ItemRepository itemRepository;
    private final ReportProcessingService reportProcessingService;
    private final ItemImgRepository itemImgRepository;
    private final ItemLinkRepository itemLinkRepository;

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
        ItemReport itemReport = itemReportRepository.findById(itemReportId)
                .orElseThrow(ItemReportNotFoundException::new);

        Item item = itemRepository.findById(itemReport.getItem().getId())
                .orElseThrow(ItemNotFoundException::new);

        List<ItemImgResDto> itemImgResDtos = itemImgRepository.findAllByItemId(item.getId())
                .stream()
                .map(ItemImgResDto::of)
                .toList();

        List<ItemLinkResDto> itemLinkResDtos = itemLinkRepository.findAllByItemId(item.getId())
                .stream()
                .map(ItemLinkResDto::of)
                .toList();

        CelebSearchResDto celebDto = Optional.ofNullable(item.getCeleb())
                .map(CelebSearchResDto::of)
                .orElse(null);

        BrandSearchResDto brandDto = Optional.ofNullable(item.getBrand())
                .map(BrandSearchResDto::of)
                .orElse(null);

        ItemCategoryDto itemCategoryDto = Optional.ofNullable(item.getCategory())
                .map(ItemCategoryDto::of)
                .orElse(null);

        return ItemReportDetailDto.of(
                itemReport.getReporter().getId(),
                itemReport.getReporter().getNickname(),
                item.getUser().getId(),
                item.getUser().getNickname(),
                itemReportId,
                itemReport.getItemReportReason(),
                itemReport.getContent(),
                itemReport.getReportStatus(),
                itemImgResDtos,
                itemLinkResDtos,
                celebDto,
                brandDto,
                itemCategoryDto,
                item.getAdditionalInfo(),
                item.getColor(),
                item.getName(),
                item.getPrice(),
                item.getWhenDiscovery(),
                item.getWhereDiscovery(),
                itemReport.getCreatedAt(),
                itemReport.getUpdatedAt()
        );
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
