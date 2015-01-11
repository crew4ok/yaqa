package com.yaqa.dao;

import com.yaqa.dao.entity.TagEntity;
import com.yaqa.model.Tag;

import java.util.List;
import java.util.Map;

public interface TagDao extends GenericDao<TagEntity> {

    TagEntity getByName(String tagName);

    TagEntity findByName(String tagName);

    Map<Tag, TagEntity> mapTagsToEntities(List<Tag> tags);

}
