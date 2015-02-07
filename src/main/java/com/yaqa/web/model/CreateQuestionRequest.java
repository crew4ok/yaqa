package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import java.util.List;

public class CreateQuestionRequest {
    private final String body;
    private final List<Tag> tags;
    private final List<Long> imageIds;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty("body") String body,
                                 @JsonProperty("tags") List<Tag> tags,
                                 @JsonProperty("imageIds") List<Long> imageIds) {
        this.body = body;
        this.tags = tags;
        this.imageIds = imageIds;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }
}
