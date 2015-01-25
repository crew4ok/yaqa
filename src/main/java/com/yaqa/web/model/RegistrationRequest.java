package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationRequest {
    @NotNull
    @Size(min = 3)
    private final String username;
    private final String password;
    private final Long profileImageId;

    private final String firstName;
    private final String lastName;

    private final String email;

    @JsonCreator
    public RegistrationRequest(@JsonProperty("username") String username,
                               @JsonProperty("password") String password,
                               @JsonProperty("profileImageId") Long profileImageId,
                               @JsonProperty("firstName") String firstName,
                               @JsonProperty("lastName") String lastName,
                               @JsonProperty("email") String email) {
        this.username = username;
        this.password = password;
        this.profileImageId = profileImageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
