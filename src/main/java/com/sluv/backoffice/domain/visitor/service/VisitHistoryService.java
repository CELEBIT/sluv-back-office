package com.sluv.backoffice.domain.visitor.service;

import com.sluv.backoffice.domain.visitor.dto.VisitHistoryCountResDto;
import com.sluv.backoffice.domain.visitor.entity.DailyVisit;
import com.sluv.backoffice.domain.visitor.entity.VisitHistory;
import com.sluv.backoffice.domain.visitor.repository.DailyVisitRepository;
import com.sluv.backoffice.domain.visitor.repository.VisitHistoryRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitHistoryService {
    private final VisitHistoryRepository visitHistoryRepository;
    private final DailyVisitRepository dailyVisitRepository;

    public VisitHistoryCountResDto getVisitHistoryCount() {
        List<VisitHistory> visitHistoryFor10Days = visitHistoryRepository.getVisitHistoryFor10Days();
        List<DailyVisit> allDailyVisit = dailyVisitRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        Long todayCount = getDayCount(now, allDailyVisit);
        Long yesterdayCount = getDayCount(now.minusDays(1), allDailyVisit);
        List<Long> countGraph = getCountGraph(visitHistoryFor10Days, todayCount);

        return VisitHistoryCountResDto.of(todayCount, yesterdayCount, countGraph);
    }

    private Long getDayCount(LocalDateTime now, List<DailyVisit> allDailyVisit) {
        return allDailyVisit.stream()
                .filter(dailyVisit -> dailyVisit.getCreatedAt().getDayOfMonth() == now.getDayOfMonth())
                .count();
    }

    private static List<Long> getCountGraph(List<VisitHistory> visitHistoryFor10Days, Long todayCount) {
        List<Long> countGraph = new ArrayList<>();
        for (VisitHistory visitHistory : visitHistoryFor10Days) {
            countGraph.add(visitHistory.getVisitCount());
        }
        countGraph.add(todayCount);
        return countGraph;
    }
}
