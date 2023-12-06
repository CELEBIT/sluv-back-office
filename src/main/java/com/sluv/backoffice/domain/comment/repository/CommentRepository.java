package com.sluv.backoffice.domain.comment.repository;

import com.sluv.backoffice.domain.comment.entity.Comment;
import com.sluv.backoffice.domain.comment.repository.impl.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
