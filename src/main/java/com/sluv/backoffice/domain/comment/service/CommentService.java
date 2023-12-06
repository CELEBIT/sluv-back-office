package com.sluv.backoffice.domain.comment.service;

import com.sluv.backoffice.domain.comment.dto.CommentBlockCountResDto;
import com.sluv.backoffice.domain.comment.entity.Comment;
import com.sluv.backoffice.domain.comment.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentBlockCountResDto getCommentBlockCount() {
        LocalDateTime now = LocalDateTime.now();
        List<Comment> allBlackComment = commentRepository.getAllBlockComment();
        long recentMonthCount = getRecentMonthCount(now, allBlackComment);
        List<Long> countGraph = getCountGraph(now, allBlackComment);
        return CommentBlockCountResDto.of(allBlackComment.stream().count(), recentMonthCount, countGraph);
    }

    private static List<Long> getCountGraph(LocalDateTime now, List<Comment> allBlockComment) {
        List<Long> countGraph = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            LocalDateTime startWeek = now.minusWeeks(i);
            LocalDateTime endWeek = now.minusWeeks(i - 1);
            long count = allBlockComment.stream()
                    .filter(comment ->
                            comment.getCreatedAt().isAfter(startWeek) && comment.getCreatedAt().isBefore(endWeek)
                    )
                    .count();
            countGraph.add(count);
        }
        return countGraph;
    }

    private static long getRecentMonthCount(LocalDateTime now, List<Comment> allBlockComment) {
        return allBlockComment.stream()
                .filter(comment -> comment.getCreatedAt().isAfter(now.minusMonths(1)))
                .count();
    }
}
