package com.yaqa.dao;

import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.QuestionEntity;

import java.util.List;

public interface CommentDao extends GenericDao<CommentEntity> {


    List<CommentEntity> findByQuestion(QuestionEntity questionEntity);
}
