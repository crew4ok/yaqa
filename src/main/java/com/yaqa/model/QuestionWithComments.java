package com.yaqa.model;

import com.yaqa.dao.entity.QuestionEntity;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionWithComments {
    private final Question question;
    private final List<Comment> comments;

    public static QuestionWithComments of(QuestionEntity questionEntity) {
        return new QuestionWithComments(
                Question.of(questionEntity),
                questionEntity.getComments().stream().map(Comment::of).collect(Collectors.toList())
        );
    }

    public QuestionWithComments(Question question, List<Comment> comments) {
        this.question = question;
        this.comments = comments;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
