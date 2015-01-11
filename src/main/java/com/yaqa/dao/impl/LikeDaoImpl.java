package com.yaqa.dao.impl;

import com.yaqa.dao.LikeDao;
import com.yaqa.dao.entity.LikeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDaoImpl extends GenericDaoImpl<LikeEntity> implements LikeDao {
    public LikeDaoImpl() {
        super(LikeEntity.class);
    }
}
