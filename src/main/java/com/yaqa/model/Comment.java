package com.yaqa.model;

import com.yaqa.dao.entity.CommentEntity;
import org.joda.time.LocalDateTime;

public class Comment {
    private final String body;
    private final LocalDateTime creationDate;

    public static Comment of(CommentEntity commentEntity) {
        return new Comment(commentEntity.getBody(), commentEntity.getCreationDate());
    }

    public Comment(String body, LocalDateTime creationDate) {
        this.body = body;
        this.creationDate = creationDate;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
