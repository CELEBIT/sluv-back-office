package com.sluv.backoffice.domain.visit.controller;

import com.sluv.backoffice.domain.visit.dto.VisitHistoryCountResDto;
import com.sluv.backoffice.domain.visit.service.VisitHistoryService;
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
@RequestMapping("/backoffice/visit-history")
public class VisitHistoryController {
    private final VisitHistoryService visitHistoryService;

    @Operation(
            summary = "대시보드 - 일일 누적 접속자 수 조회.",
            description = "일일 누적 접속자 수를 조회한다.\n"
                    + "1. 오늘 누적 접속자 수.\n"
                    + "2. 어제 동시간 대비 누적 접속자 수 증가 퍼센트."
                    + "3. 10일간 누적 접속자 수 그래프."
    )
    @GetMapping("/dailyCount")
    public ResponseEntity<SuccessDataResponse<VisitHistoryCountResDto>> getVisitHistoryCount() {
        return ResponseEntity.ok().body(
                SuccessDataResponse.<VisitHistoryCountResDto>builder()
                        .result(visitHistoryService.getVisitHistoryCount())
                        .build()
        );
    }
}