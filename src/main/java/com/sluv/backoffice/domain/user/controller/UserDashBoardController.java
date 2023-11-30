package com.sluv.backoffice.domain.user.controller;

import com.sluv.backoffice.domain.user.dto.UserCountByGenderResDto;
import com.sluv.backoffice.domain.user.service.UserService;
import com.sluv.backoffice.global.common.response.SuccessDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/backoffice/user/dashBoard")
public class UserDashBoardController {

    private final UserService userService;

    @GetMapping("/gender")
    public ResponseEntity<SuccessDataResponse<UserCountByGenderResDto>> getUserCountByGender() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserCountByGenderResDto>builder()
                        .result(userService.getUserCountByGender())
                        .build()
        );
    }
}
