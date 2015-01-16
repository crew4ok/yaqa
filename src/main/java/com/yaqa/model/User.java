package com.yaqa.model;

import com.yaqa.dao.entity.UserEntity;

public class User {
    private final Long id;
    private final String username;
    private final String profileImage;

    public static User of(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getProfileImage());
    }

    public User(Long id, String username, String profileImage) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
