package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PostCommentRequest {
    @NotNull
    private final String body;

    private final List<String> images;

    @JsonCreator
    public PostCommentRequest(@JsonProperty("body") String body,
                              @JsonProperty("images") List<String> images) {
        this.body = body;
        this.images = images;
    }

    public String getBody() {
        return body;
    }

    public List<String> getImages() {
        return images;
    }
}
