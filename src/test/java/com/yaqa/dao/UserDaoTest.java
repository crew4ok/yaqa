package com.yaqa.dao;

import com.yaqa.config.TestDaoConfig;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
@TestDaoConfig
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageDao imageDao;

    public void getByUsername_hp() {
        final String username = "username";
        final String password = "password";
        final UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setPassword(password);

        userDao.save(user);

        final UserEntity actualUser = userDao.getByUsername(username);

        assertNotNull(actualUser.getId());
        assertEquals(actualUser.getUsername(), username);
        assertEquals(actualUser.getPassword(), password);
    }

    public void saveUser_hp() {
        final ImageEntity imageEntity = new ImageEntity(new byte[]{1, 2, 3, 4});
        imageDao.save(imageEntity);

        final UserEntity user = new UserEntity(
                "username",
                "password",
                "firstName",
                "lastName",
                "email",
                imageEntity);

        userDao.save(user);

        final UserEntity actualUser = userDao.getById(user.getId());
        assertEquals(actualUser.getUsername(), user.getUsername());
        assertEquals(actualUser.getPassword(), user.getPassword());
        assertEquals(actualUser.getFirstName(), user.getFirstName());
        assertEquals(actualUser.getLastName(), user.getLastName());
        assertEquals(actualUser.getEmail(), user.getEmail());
        assertEquals(actualUser.getProfileImage().getContent(), imageEntity.getContent());
    }
}
