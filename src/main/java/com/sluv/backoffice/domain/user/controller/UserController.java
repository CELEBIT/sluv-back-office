package com.sluv.backoffice.domain.user.controller;

import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.domain.user.service.UserService;
import com.sluv.backoffice.global.common.response.ErrorResponse;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import com.sluv.backoffice.global.common.response.SuccessDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backoffice/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "전체 유저 조회",
            description = "모든 유저의 상태를 포함한 정보를 조회한다"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청성공"),
            @ApiResponse(responseCode = "5000", description = "서버내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5001", description = "DB 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<SuccessDataResponse<PaginationResDto<UserInfoDto>>> getAllUserInfo(Pageable pageable) {
        return ResponseEntity.ok().body(SuccessDataResponse.<PaginationResDto<UserInfoDto>>builder()
                .result(userService.getAllUserInfo(pageable))
                .build());
    }
}
