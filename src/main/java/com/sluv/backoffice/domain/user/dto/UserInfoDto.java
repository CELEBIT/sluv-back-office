package com.sluv.backoffice.domain.user.dto;

import com.sluv.backoffice.domain.user.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String nickname;
    private String profileImgUrl;
    private UserStatus userStatus;
    @Schema(description = "처리 대기 중인 신고 수")
    private Long waitingReportCount;
    @Schema(description = "승인된 누적 신고 수")
    private Long reportStackCount;
}
