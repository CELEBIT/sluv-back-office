package com.sluv.backoffice.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMNotificationRequestDto {

    private Long userId;
    private String title;
    private String body;
}
