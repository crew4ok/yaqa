package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PostCommentRequest {
    @NotNull
    private final String body;

    private final List<Long> imageIds;

    @JsonCreator
    public PostCommentRequest(@JsonProperty("body") String body,
                              @JsonProperty("imageIds") List<Long> imageIds) {
        this.body = body;
        this.imageIds = imageIds;
    }

    public String getBody() {
        return body;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }
}
