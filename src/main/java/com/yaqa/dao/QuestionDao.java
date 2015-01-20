package com.yaqa.dao;

import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.UserEntity;

import java.util.List;

public interface QuestionDao extends GenericDao<QuestionEntity> {
    List<QuestionEntity> getByTagName(String tagName);

    Long getLikeCount(QuestionEntity question);

    List<QuestionEntity> getByUserTagsLimited(UserEntity author, int limit);

    List<QuestionEntity> getByUserTagsLimited(UserEntity author, Long lastId, int limit);

    List<QuestionEntity> getLastLimited(int limit);

    List<QuestionEntity> getBelowIdLimited(Long lastId, int limited);

    List<QuestionEntity> getByAuthorLimited(UserEntity author, int limit);

    List<QuestionEntity> getByAuthorLimited(UserEntity author, Long lastId, int limit);

    List<QuestionEntity> getCommentedByAuthorLimited(UserEntity author, int limit);

    List<QuestionEntity> getCommentedByAuthorLimited(UserEntity author, Long lastId, int limit);
}
