package com.yaqa.model;

import com.yaqa.dao.entity.UserEntity;

public class User {
    private final Long id;
    private final String username;

    public static User of(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername());
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}
