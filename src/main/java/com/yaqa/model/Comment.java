package com.yaqa.model;

import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.UserEntity;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

public class Comment {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final User author;
    private final Integer likeCount;
    private final LikeResult.Type likeType;
    private final List<Long> imageIds;

    public static Comment of(CommentEntity commentEntity, UserEntity currentUser) {
        boolean likedByCurrentUser = commentEntity.getLikes()
                .stream()
                .anyMatch(l -> l.getLiker().getId().equals(currentUser.getId()));

        return new Comment(
                commentEntity.getId(),
                commentEntity.getBody(),
                commentEntity.getCreationDate(),
                User.of(commentEntity.getAuthor()),
                commentEntity.getLikes().size(),
                likedByCurrentUser ? LikeResult.Type.LIKE : LikeResult.Type.DISLIKE,
                commentEntity.getImages().stream().map(ImageEntity::getId).collect(Collectors.toList())
        );
    }

    public Comment(Long id, String body, LocalDateTime creationDate, User author, Integer likeCount,
                   LikeResult.Type likeType, List<Long> imageIds) {
        this.id = id;
        this.body = body;
        this.creationDate = creationDate;
        this.author = author;
        this.likeCount = likeCount;
        this.likeType = likeType;
        this.imageIds = imageIds;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public LikeResult.Type getLikeType() {
        return likeType;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }
}
