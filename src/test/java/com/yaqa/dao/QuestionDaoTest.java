package com.yaqa.dao;

import com.yaqa.config.DaoConfig;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;

@Test
@ContextConfiguration(classes = DaoConfig.class)
public class QuestionDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private UserDao userDao;

    public void getByTagName() {
        final String firstTagName = "tag1";
        final String secondTagName = "tag2";

        final QuestionEntity question = new QuestionEntity("title", "body",
                createUser(), Arrays.asList(createTag(firstTagName), createTag(secondTagName)));

        questionDao.save(question);

        final List<QuestionEntity> foundByFirstTag = questionDao.getByTagName(firstTagName);
        assertTrue(foundByFirstTag.contains(question));

        final List<QuestionEntity> foundBySecondTag = questionDao.getByTagName(secondTagName);
        assertTrue(foundBySecondTag.contains(question));
    }

    private TagEntity createTag(String tagName) {
        final TagEntity tag = new TagEntity(tagName);
        tagDao.save(tag);
        return tag;
    }

    private UserEntity createUser() {
        final UserEntity user = new UserEntity("username", "password");
        userDao.save(user);
        return user;
    }
}
