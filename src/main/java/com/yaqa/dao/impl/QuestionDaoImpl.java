package com.yaqa.dao.impl;

import com.yaqa.dao.QuestionDao;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDaoImpl extends GenericDaoImpl<QuestionEntity> implements QuestionDao {

    public QuestionDaoImpl() {
        super(QuestionEntity.class);
    }

    @Override
    public List<QuestionEntity> getByTagName(String tagName) {
        return em.createQuery("select q " +
                        " from QuestionEntity q " +
                        " inner join q.tags as t" +
                        " where t.tagName = :tagName",
                QuestionEntity.class)
                .setParameter("tagName", tagName)
                .getResultList();
    }

    @Override
    public Long getLikeCount(QuestionEntity question) {
        return em.createQuery("select count(l) " +
                        " from QuestionEntity q " +
                        " inner join q.likes l " +
                        " where q = :question ",
                Long.class)
                .setParameter("question", question)
                .getSingleResult();
    }

    @Override
    public List<QuestionEntity> getByTags(List<TagEntity> tags) {
        return em.createQuery("select q " +
                        " from QuestionEntity q " +
                        " inner join q.tags as t " +
                        " where t in :tags " +
                        " group by q.id",
                QuestionEntity.class)
                .setParameter("tags", tags)
                .getResultList();
    }

    @Override
    public List<QuestionEntity> getLastLimited(int limit) {
        return em.createQuery("select q " +
                        " from QuestionEntity q " +
                        " order by q.id desc ",
                QuestionEntity.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<QuestionEntity> getBelowIdLimited(Long lastId, int limited) {
        return em.createQuery("select q " +
                        " from QuestionEntity q " +
                        " where q.id < :lastId " +
                        " order by q.id desc ",
                QuestionEntity.class)
                .setParameter("lastId", lastId)
                .setMaxResults(limited)
                .getResultList();
    }
}
