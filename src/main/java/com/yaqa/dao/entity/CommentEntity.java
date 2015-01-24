package com.yaqa.dao.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentsIdSequence")
    @SequenceGenerator(name = "commentsIdSequence", sequenceName = "COMMENTS_ID_SEQ")
    private Long id;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String body;

    @NotNull
    @Column(updatable = false, nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime creationDate = LocalDateTime.now(DateTimeZone.UTC);

    @OneToMany
    private List<ImageEntity> images = new ArrayList<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author")
    private UserEntity author;

    @OneToMany(mappedBy = "comment")
    private List<LikeEntity> likes = new ArrayList<>();

    /**
     * Default constructor that is used by hibernate.
     */
    public CommentEntity() {
    }

    public CommentEntity(String body, UserEntity author, QuestionEntity question, List<ImageEntity> images) {
        this.body = body;
        this.author = author;
        this.question = question;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public List<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeEntity> likes) {
        this.likes = likes;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }
}
