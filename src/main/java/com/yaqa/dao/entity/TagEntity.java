package com.yaqa.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagsIdSequence")
    @SequenceGenerator(name = "tagsIdSequence", sequenceName = "TAGS_ID_SEQ")
    private Long id;

    @NotNull
    @Size(max = 32)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<QuestionEntity> taggedQuestions = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptionTags")
    private List<UserEntity> subscribedUsers = new ArrayList<>();

    /**
     * Default constructor used by hibernate.
     * */
    public TagEntity() { }

    public TagEntity(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (id != null ? !id.equals(tagEntity.id) : tagEntity.id != null) return false;
        if (tagName != null ? !tagName.equals(tagEntity.tagName) : tagEntity.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<QuestionEntity> getTaggedQuestions() {
        return taggedQuestions;
    }

    public void setTaggedQuestions(List<QuestionEntity> taggedQuestions) {
        this.taggedQuestions = taggedQuestions;
    }

    public List<UserEntity> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<UserEntity> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }
}
