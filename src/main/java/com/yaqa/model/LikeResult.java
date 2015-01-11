package com.yaqa.model;

public class LikeResult {
    public static enum Type {
        LIKE, DISLIKE
    }

    private final Long likesCount;
    private final Type type;

    public LikeResult(Long likesCount, Type type) {
        this.likesCount = likesCount;
        this.type = type;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public Type getType() {
        return type;
    }
}
