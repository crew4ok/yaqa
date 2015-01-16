package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import java.util.List;

public class UpdateUserProfileRequest {
    private final String password;
    private final List<Tag> tags;
    private final String profileImage;

    @JsonCreator
    public UpdateUserProfileRequest(@JsonProperty("password") String password,
                                    @JsonProperty("tags") List<Tag> tags,
                                    @JsonProperty("profileImage") String profileImage) {
        this.password = password;
        this.tags = tags;
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
