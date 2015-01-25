package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaqa.model.Tag;

import java.util.List;

public class UpdateUserProfileRequest {
    private final String password;

    private final String firstName;
    private final String lastName;

    private final String email;

    private final List<Tag> tags;
    private final Long profileImageId;

    @JsonCreator
    public UpdateUserProfileRequest(@JsonProperty("password") String password,
                                    @JsonProperty("firstName") String firstName,
                                    @JsonProperty("lastName") String lastName,
                                    @JsonProperty("email") String email,
                                    @JsonProperty("tags") List<Tag> tags,
                                    @JsonProperty("profileImageId") Long profileImageId) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tags = tags;
        this.profileImageId = profileImageId;
    }

    public String getPassword() {
        return password;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Long getProfileImageId() {
        return profileImageId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
