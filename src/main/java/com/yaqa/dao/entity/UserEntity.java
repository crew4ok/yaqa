package com.yaqa.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String profileImage;

    @OneToMany(mappedBy = "author")
    private List<QuestionEntity> questions;

    @OneToMany(mappedBy = "author")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "liker")
    private List<LikeEntity> likes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> subscriptionTags = new ArrayList<>();

    /**
     * Default constructor used by hibernate.
     */
    public UserEntity() { }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(String username, String password, String profileImage) {
        this(username, password);
        this.profileImage = profileImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<TagEntity> getSubscriptionTags() {
        return subscriptionTags;
    }

    public void setSubscriptionTags(List<TagEntity> subscriptionTags) {
        this.subscriptionTags = subscriptionTags;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeEntity> likes) {
        this.likes = likes;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
