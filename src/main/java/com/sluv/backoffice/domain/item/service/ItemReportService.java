package com.sluv.backoffice.domain.item.service;

import com.sluv.backoffice.domain.item.dto.ItemReportInfoDto;
import com.sluv.backoffice.domain.item.dto.UpdateItemReportResDto;
import com.sluv.backoffice.domain.item.entity.ItemReport;
import com.sluv.backoffice.domain.item.enums.ItemStatus;
import com.sluv.backoffice.domain.item.exception.ItemReportNotFoundException;
import com.sluv.backoffice.domain.item.repository.ItemReportRepository;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReportStack;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.domain.user.exception.InvalidReportStatusException;
import com.sluv.backoffice.domain.user.repository.UserReportStackRepository;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;
    private final UserReportStackRepository userReportStackRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<ItemReportInfoDto> getAllItemReport(Pageable pageable, ReportStatus reportStatus) {
        Page<ItemReportInfoDto> itemReport = itemReportRepository.getAllItemReport(pageable, reportStatus);

        return PaginationResDto.<ItemReportInfoDto>builder()
                .hasNext(itemReport.hasNext())
                .page(itemReport.getNumber())
                .content(itemReport.getContent())
                .build();
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

        itemReport.changeItemReportStatus(reportStatus);

        if (reportStatus == ReportStatus.COMPLETED) {
            updateItemReportCompleted(itemReport);
        }
        return UpdateItemReportResDto.of(itemReport.getReportStatus());
    }

    /**
     *TODO:추후 신고자 및 피신고자 알림 설정 추가
     */
    private void updateItemReportCompleted(ItemReport itemReport) {
        itemReport.getItem().changeItemStatus(ItemStatus.BLOCKED);

        User reportedUser = itemReport.getItem().getUser();
        userReportStackRepository.save(UserReportStack.toEntity(reportedUser));

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long reportCount = userReportStackRepository.countByReportedAndCreatedAtAfter(reportedUser, oneMonthAgo);

        if (reportCount >= 3) {
            reportedUser.changeUserStatus(UserStatus.BLOCKED);
        }
    }
}
