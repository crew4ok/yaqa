package com.yaqa.web.model;

public class LikeResponse {
    private final Long likesCounts;

    public LikeResponse(Long likesCounts) {
        this.likesCounts = likesCounts;
    }

    public Long getLikesCounts() {
        return likesCounts;
    }
}
