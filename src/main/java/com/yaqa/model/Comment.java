package com.yaqa.model;

import com.yaqa.dao.entity.CommentEntity;
import org.joda.time.LocalDateTime;

public class Comment {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final User author;

    public static Comment of(CommentEntity commentEntity) {
        return new Comment(
                commentEntity.getId(),
                commentEntity.getBody(),
                commentEntity.getCreationDate(),
                User.of(commentEntity.getAuthor())
        );
    }

    public Comment(Long id, String body, LocalDateTime creationDate, User author) {
        this.id = id;
        this.body = body;
        this.creationDate = creationDate;
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }
}
