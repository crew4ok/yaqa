package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateQuestionRequest {
    @NotNull
    @Size(max = 128)
    private final String title;

    @NotNull
    private final String body;

    @NotNull
    private final List<Tag> tags;

    @JsonCreator
    public CreateQuestionRequest(@JsonProperty("title") String title,
                                 @JsonProperty("body") String body,
                                 @JsonProperty("tags") List<Tag> tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
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
