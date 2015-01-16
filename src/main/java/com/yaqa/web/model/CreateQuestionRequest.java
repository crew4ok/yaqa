package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateQuestionRequest {
    @NotNull
    private final String body;

    @NotNull
    private final List<Tag> tags;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty("body") String body,
                                 @JsonProperty("tags") List<Tag> tags) {
        this.body = body;
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
