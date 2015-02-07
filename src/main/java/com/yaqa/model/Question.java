package com.yaqa.model;

import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.UserEntity;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final User author;
    private final Integer likesCount;
    private final LikeResult.Type likeType;
    private final Integer commentsCount;
    private final List<Tag> tags;
    private final List<Long> imageIds;

    public static Question of(QuestionEntity questionEntity, UserEntity currentUser) {
        boolean likedByCurrentUser = questionEntity.getLikes()
                .stream()
                .anyMatch(l -> l.getLiker().getId().equals(currentUser.getId()));

        return new Question(
                questionEntity.getId(),
                questionEntity.getBody(),
                questionEntity.getCreationDate(),
                User.of(questionEntity.getAuthor()),
                questionEntity.getLikes().size(),
                likedByCurrentUser ? LikeResult.Type.LIKE : LikeResult.Type.DISLIKE,
                questionEntity.getComments().size(),
                questionEntity.getTags().stream().map(Tag::of).collect(Collectors.toList()),
                questionEntity.getImages().stream().map(ImageEntity::getId).collect(Collectors.toList())
        );
    }

    public Question(String body, List<Tag> tags) {
        this(null, body, null, null, null, null, null, tags, null);
    }

    public Question(Long id, String body, LocalDateTime creationDate, User author, Integer likesCount,
                    LikeResult.Type likeType, Integer commentsCount, List<Tag> tags, List<Long> imageIds) {
        this.id = id;
        this.body = body;
        this.creationDate = creationDate;
        this.author = author;
        this.likesCount = likesCount;
        this.likeType = likeType;
        this.commentsCount = commentsCount;
        this.tags = tags;
        this.imageIds = imageIds;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public LikeResult.Type getLikeType() {
        return likeType;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }
}
