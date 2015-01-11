package com.yaqa.dao.impl;

import com.yaqa.dao.TagDao;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.exception.NotFoundException;
import com.yaqa.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagDaoImpl extends GenericDaoImpl<TagEntity> implements TagDao {
    public TagDaoImpl() {
        super(TagEntity.class);
    }

    @Override
    public TagEntity getByName(String tagName) {
        try {
            return em.createQuery("select t " +
                            " from TagEntity t " +
                            " where t.tagName = :tagName",
                    TagEntity.class)
                    .setParameter("tagName", tagName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException(TagEntity.class, tagName);
        }
    }

    @Override
    public TagEntity findByName(String tagName) {
        try {
            return em.createQuery("select t " +
                            " from TagEntity t " +
                            " where t.tagName = :tagName",
                    TagEntity.class)
                    .setParameter("tagName", tagName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Map<Tag, TagEntity> mapTagsToEntities(List<Tag> tags) {
        final Map<Tag, TagEntity> map = new HashMap<>();

        for (Tag tag : tags) {
            final TagEntity tagEntity = findByName(tag.getTagName());
            if (tagEntity != null) {
                map.put(tag, tagEntity);
            }
        }

        return map;
    }
}
