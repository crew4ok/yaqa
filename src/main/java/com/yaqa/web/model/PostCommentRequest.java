package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PostCommentRequest {
    @NotNull
    private final String body;

    @JsonCreator
    public PostCommentRequest(@JsonProperty("body") String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
