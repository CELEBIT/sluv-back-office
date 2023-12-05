package com.sluv.backoffice.domain.user.controller;

import com.sluv.backoffice.domain.user.dto.UserAccountCountResDto;
import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto;
import com.sluv.backoffice.domain.user.service.UserService;
import com.sluv.backoffice.global.common.response.SuccessDataResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "대시보드 - 유저의 성별 분포 조회",
            description = "대시보드에서 유저의 성별 분포를 출력한다"
    )
    @GetMapping("/gender")
    public ResponseEntity<SuccessDataResponse<UserCountByCategoryResDto>> getUserCountByGender() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserCountByCategoryResDto>builder()
                        .result(userService.getUserCountByGender())
                        .build()
        );
    }


    @Operation(
            summary = "대시보드 - 유저의 연령대 분포 조회",
            description = "대시보드에서 유저의 연령대 분포를 출력한다"
    )
    @GetMapping("/age")
    public ResponseEntity<SuccessDataResponse<UserCountByCategoryResDto>> getUserCountByAge() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserCountByCategoryResDto>builder()
                        .result(userService.getUserCountByAge())
                        .build()
        );
    }

    @Operation(
            summary = "대시보드 - 가입자 수 조회 기능",
            description = "대시보드에서 가입자 수 조회 기능\n"
                    + "1. 전체 가입자 수\n"
                    + "2. 지난 1달간 증가 수\n"
                    + "3. 지난 10주간 그래프"

    )
    @GetMapping("/accountCount")
    public ResponseEntity<SuccessDataResponse<UserAccountCountResDto>> getUserCount() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserAccountCountResDto>builder()
                        .result(userService.getUserAccountCount())
                        .build()
        );
    }
}
