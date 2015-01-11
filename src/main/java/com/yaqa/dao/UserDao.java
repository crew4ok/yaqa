package com.yaqa.dao;

import com.yaqa.dao.entity.UserEntity;

public interface UserDao extends GenericDao<UserEntity> {

    UserEntity getByUsername(String username);

}
