package com.yaqa.dao;

import com.yaqa.config.DaoConfig;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("test")
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

    public void getByTags_hp() {
        final List<TagEntity> tags = Arrays.asList(
                createTag("tag1"), createTag("tag2"), createTag("tag3"), createTag("tag4")
        );

        final UserEntity user = createUser(tags);

        final List<QuestionEntity> questions = Arrays.asList(
                createQuestion("title", "body", user, Collections.singletonList(tags.get(0))),
                createQuestion("title", "body", user, tags.subList(0, 2)),
                createQuestion("title", "body", user, tags.subList(1, 3))
        );

        final List<QuestionEntity> actualQuestions = questionDao.getByTags(tags);

        final Comparator<QuestionEntity> comparator = (q1, q2) -> q1.getId().compareTo(q2.getId());
        questions.sort(comparator);
        actualQuestions.sort(comparator);

        assertEquals(actualQuestions, questions);
    }

    private QuestionEntity createQuestion(String title, String body, UserEntity user, List<TagEntity> tags) {
        final QuestionEntity question = new QuestionEntity(title, body, user, tags);
        questionDao.save(question);
        return question;
    }

    private TagEntity createTag(String tagName) {
        final TagEntity tag = new TagEntity(tagName);
        tagDao.save(tag);
        return tag;
    }

    private UserEntity createUser() {
        return createUser(Collections.emptyList());
    }

    private UserEntity createUser(List<TagEntity> tags) {
        final UserEntity user = new UserEntity("username", "password");
        user.setSubscriptionTags(tags);
        userDao.save(user);
        return user;

    }
}
