package com.sluv.backoffice.domain.item.controller;

import com.sluv.backoffice.domain.item.service.ItemService;
import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto;
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
@RequestMapping("/backoffice/item/dashBoard")
public class ItemDashBoardController {
    private final ItemService itemService;

    @Operation(
            summary = "대시보드 - 유저의 연령대 분포",
            description = "대시보드에서 유저의 연령대 분포를 출력한다"
    )
    @GetMapping("/celebCategory")
    public ResponseEntity<SuccessDataResponse<UserCountByCategoryResDto>> getItemCountByCelebCategory() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<UserCountByCategoryResDto>builder()
                        .result(itemService.getItemCountByCelebCategory())
                        .build()
        );
    }

}
