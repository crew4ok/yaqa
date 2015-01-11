package com.yaqa.model;

import com.yaqa.dao.entity.CommentEntity;

public class Comment {
    private final String body;

    public static Comment of(CommentEntity commentEntity) {
        return new Comment(commentEntity.getBody());
    }

    public Comment(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
