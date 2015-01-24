package com.yaqa.model;

import com.yaqa.dao.entity.UserEntity;

public class User {
    private final Long id;
    private final String username;

    private final String firstName;
    private final String lastName;

    private final String email;

    private final Long profileImageId;

    public static User of(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getProfileImage() != null ? userEntity.getProfileImage().getId() : null
        );
    }

    public User(Long id, String username, String firstName, String lastName, String email, Long profileImageId) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileImageId = profileImageId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", profileImageId='" + profileImageId + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
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
