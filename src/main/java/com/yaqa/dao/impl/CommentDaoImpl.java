package com.yaqa.dao.impl;

import com.yaqa.dao.CommentDao;
import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl extends GenericDaoImpl<CommentEntity> implements CommentDao {
    public CommentDaoImpl() {
        super(CommentEntity.class);
    }

    @Override
    public List<CommentEntity> findByQuestion(QuestionEntity questionEntity) {
        return em.createQuery("select c " +
                        " from CommentEntity c " +
                        " where c.question = :question ",
                CommentEntity.class)
                .setParameter("question", questionEntity)
                .getResultList();
    }

    @Override
    public Long getLikeCount(CommentEntity comment) {
        return em.createQuery("select count(l) " +
                        " from CommentEntity c " +
                        " inner join c.likes as l " +
                        " where c = :comment",
                Long.class)
                .setParameter("comment", comment)
                .getSingleResult();
    }
}
