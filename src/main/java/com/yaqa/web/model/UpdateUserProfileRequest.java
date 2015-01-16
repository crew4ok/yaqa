package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import java.util.List;

public class UpdateUserProfileRequest {
    private final String password;
    private final List<Tag> tags;

    @JsonCreator
    public UpdateUserProfileRequest(@JsonProperty("password") String password,
                                    @JsonProperty("tags") List<Tag> tags) {
        this.password = password;
        this.tags = tags;
    }

    public String getPassword() {
        return password;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
