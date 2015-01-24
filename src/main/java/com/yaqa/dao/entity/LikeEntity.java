package com.yaqa.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likesIdSequence")
    @SequenceGenerator(name = "likesIdSequence", sequenceName = "LIKES_ID_SEQ")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "liker")
    private UserEntity liker;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @ManyToOne
    @JoinColumn(name = "question_id")
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
