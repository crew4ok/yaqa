package com.yaqa.dao;

import com.yaqa.dao.entity.QuestionEntity;

import java.util.List;

public interface QuestionDao extends GenericDao<QuestionEntity> {
    List<QuestionEntity> getByTagName(String tagName);

    Long getLikeCount(QuestionEntity question);
}
