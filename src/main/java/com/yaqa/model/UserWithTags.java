package com.yaqa.model;

import com.yaqa.dao.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserWithTags {
    private final User user;
    private final List<Tag> tags;

    public static UserWithTags of(UserEntity userEntity) {
        return new UserWithTags(
                User.of(userEntity),
                userEntity.getSubscriptionTags().stream().map(Tag::of).collect(Collectors.toList())
        );
    }

    public UserWithTags(User user, List<Tag> tags) {
        this.user = user;
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
