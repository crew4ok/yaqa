package com.yaqa.dao.impl;

import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<UserEntity> implements UserDao {
    public UserDaoImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity getByUsername(String username) {
        return em.createQuery("select u " +
                        " from UserEntity u " +
                        " where u.username = :username",
                UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
