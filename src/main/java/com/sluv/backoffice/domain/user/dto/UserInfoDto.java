package com.sluv.backoffice.domain.user.dto;

import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.enums.UserStatus;
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
    private Long waitingReportCount;
    private Long reportStackCount;

    public static UserInfoDto of(User user, Long waitingReportCount, Long reportStackCount) {
        return new UserInfoDto(
                user.getNickname(),
                user.getProfileImgUrl(),
                user.getUserStatus(),
                waitingReportCount,
                reportStackCount
        );
    }
}
