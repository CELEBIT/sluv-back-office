package com.sluv.backoffice.domain.item.dto;

import com.sluv.backoffice.domain.item.enums.ItemReportReason;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemReportDetailDto {

    private Long reporterId;
    private String reporterNickname;
    private Long reportedId;
    private String reportedNickname;
    private Long reportId;
    @Schema(description = "아이템 신고 이유(enums)")
    private ItemReportReason reportReason;
    @Schema(description = "신고 상세 내용")
    private String content;
    @Schema(description = "신고 접수 상태")
    private ReportStatus reportStatus;
    @Schema(description = "item 이미지 리스트")
    private List<ItemImgResDto> imgList;
    @Schema(description = "item 링크 리스트")
    private List<ItemLinkResDto> linkList;
    private CelebSearchResDto celeb;
    private BrandSearchResDto brand;
    private ItemCategoryDto category;
    @Schema(description = "추가정보")
    private String additional_info;
    private String color;
    private String name;
    private Integer price;
    @Schema(description = "발견 시간 ex)2021-11-20T09:10:20")
    private LocalDateTime whenDiscovery;
    @Schema(description = "발견 장소")
    private String whereDiscovery;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ItemReportDetailDto of(Long reporterId, String reporterNickname, Long reportedId,
                                         String reportedNickname, Long reportId, ItemReportReason reportReason,
                                         String content, ReportStatus reportStatus, List<ItemImgResDto> imgList,
                                         List<ItemLinkResDto> linkList, CelebSearchResDto celeb, BrandSearchResDto brand,
                                         ItemCategoryDto category, String additional_info, String color, String name,
                                         Integer price, LocalDateTime whenDiscovery, String whereDiscovery,
                                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ItemReportDetailDto(
            reporterId,
            reporterNickname,
            reportedId,
            reportedNickname,
            reportId,
            reportReason,
            content,
            reportStatus,
            imgList,
            linkList,
            celeb,
            brand,
            category,
            additional_info,
            color,
            name,
            price,
            whenDiscovery,
            whereDiscovery,
            createdAt,
            updatedAt
        );
    }
}