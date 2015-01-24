package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateQuestionRequest {
    @NotNull
    private final String body;
    private final List<Tag> tags;
    private final List<String> images;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty("body") String body,
                                 @JsonProperty("tags") List<Tag> tags,
                                 @JsonProperty("images") List<String> images) {
        this.body = body;
        this.tags = tags;
        this.images = images;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getImages() {
        return images;
    }
}
