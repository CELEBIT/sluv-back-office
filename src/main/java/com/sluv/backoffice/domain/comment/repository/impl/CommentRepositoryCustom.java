package com.sluv.backoffice.domain.comment.repository.impl;

import com.sluv.backoffice.domain.comment.entity.Comment;
import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> getAllBlockComment();
}
