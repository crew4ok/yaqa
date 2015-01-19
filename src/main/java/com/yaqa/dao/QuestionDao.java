package com.yaqa.dao;

import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;

import java.util.List;

public interface QuestionDao extends GenericDao<QuestionEntity> {
    List<QuestionEntity> getByTagName(String tagName);

    Long getLikeCount(QuestionEntity question);

    List<QuestionEntity> getByTags(List<TagEntity> tags);

    List<QuestionEntity> getLastLimited(int limit);

    List<QuestionEntity> getBelowIdLimited(Long lastId, int limited);
}
