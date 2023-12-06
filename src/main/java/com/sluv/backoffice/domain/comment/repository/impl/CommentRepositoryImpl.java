package com.sluv.backoffice.domain.comment.repository.impl;

import static com.sluv.backoffice.domain.comment.entity.QComment.comment;
import static com.sluv.backoffice.domain.comment.enums.CommentStatus.BLOCKED;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sluv.backoffice.domain.comment.entity.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getAllBlockComment() {
        return jpaQueryFactory.selectFrom(comment)
                .where(comment.commentStatus.eq(BLOCKED))
                .fetch();
    }
}
