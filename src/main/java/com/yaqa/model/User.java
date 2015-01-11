package com.yaqa.model;

import com.yaqa.dao.entity.UserEntity;

public class User {
    private final Long id;
    private final String username;
    private final String password;

    public static User of(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}
