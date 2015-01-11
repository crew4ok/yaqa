package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Tag {
    @NotNull
    @Size(max = 32)
    private final String tagName;

    @JsonCreator
    public Tag(@JsonProperty("tagName") String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
