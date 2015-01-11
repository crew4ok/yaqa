package com.yaqa.model;

import com.yaqa.dao.entity.QuestionEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private final Long id;
    private final String title;
    private final String body;
    private final List<Tag> tags;

    public static Question of(QuestionEntity questionEntity) {
        return new Question(
                questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.getBody(),
                questionEntity.getTags().stream().map(Tag::of).collect(Collectors.toList())
        );
    }

    public Question(Long id, String title, String body, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
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

    public List<Tag> getTags() {
        return tags;
    }
}
