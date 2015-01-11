package com.yaqa.service.impl;

import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.model.User;
import com.yaqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerNewUser(User user) {
        userDao.save(new UserEntity(user.getUsername(), passwordEncoder.encode(user.getPassword())));
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        final org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) currentAuthentication.getPrincipal();
        final String username = user.getUsername();

        return User.of(userDao.getByUsername(username));
    }
}
