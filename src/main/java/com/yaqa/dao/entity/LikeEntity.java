package com.yaqa.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserEntity liker;

    @ManyToOne
    private CommentEntity comment;

    @ManyToOne
    private QuestionEntity question;

    /**
     * Default constructor that is used by hibernate.
     */
    public LikeEntity() {
    }

    public LikeEntity(UserEntity liker, CommentEntity comment) {
        this.liker = liker;
        this.comment = comment;
    }

    public LikeEntity(UserEntity liker, QuestionEntity question) {
        this.liker = liker;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getLiker() {
        return liker;
    }

    public void setLiker(UserEntity liker) {
        this.liker = liker;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
