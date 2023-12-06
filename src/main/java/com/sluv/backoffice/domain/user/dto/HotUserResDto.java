package com.sluv.backoffice.domain.user.dto;

import com.sluv.backoffice.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotUserResDto {
    private Long id;
    private String nickName;
    private String imgUrl;
    @Schema(description = "해당 유저의 Follower 수")
    private Long followerCount;

    public static HotUserResDto of(User user, Long followerCount) {
        return HotUserResDto.builder()
                .id(user.getId())
                .nickName(user.getNickname())
                .imgUrl(user.getProfileImgUrl())
                .followerCount(followerCount)
                .build();
    }
}
