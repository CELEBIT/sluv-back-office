package com.sluv.backoffice.domain.user.controller;

import com.sluv.backoffice.domain.user.dto.UpdateUserReportResDto;
import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto;
import com.sluv.backoffice.domain.user.service.UserService;
import com.sluv.backoffice.global.common.response.SuccessDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/backoffice/user/dashBoard")
public class UserDashBoardController {

    private final UserService userService;

    @GetMapping("/gender")
    public ResponseEntity<SuccessDataResponse<UserCountByCategoryResDto>> getUserCountByGender() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserCountByCategoryResDto>builder()
                        .result(userService.getUserCountByGender())
                        .build()
        );
    }
}
