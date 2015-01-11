package com.yaqa.model;

import com.yaqa.dao.entity.QuestionEntity;

public class Question {
    private final Long id;
    private final String title;
    private final String body;

    public static Question of(QuestionEntity questionEntity) {
        return new Question(
                questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.getBody()
        );
    }

    public Question(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
