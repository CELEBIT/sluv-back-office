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

    private String email;
    private String nickname;
    private String profileImgUrl;
    private UserStatus userStatus;

    public static UserInfoDto of(User user) {
        return new UserInfoDto(
                user.getEmail(),
                user.getNickname(),
                user.getProfileImgUrl(),
                user.getUserStatus()
        );
    }
}
