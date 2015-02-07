package com.yaqa.service;


import com.yaqa.config.DaoConfig;
import com.yaqa.config.ServiceConfig;
import com.yaqa.dao.ImageDao;
import com.yaqa.dao.QuestionDao;
import com.yaqa.dao.TagDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.model.Question;
import com.yaqa.model.QuestionWithComments;
import com.yaqa.model.Tag;
import com.yaqa.util.TestUtils;
import com.yaqa.web.model.CreateQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

@Test
@ContextConfiguration(classes = {DaoConfig.class, ServiceConfig.class})
@ActiveProfiles("test")
public class QuestionServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private TagDao tagDao;

    private UserEntity user;

    @BeforeMethod
    public void createUser() {
        user = new UserEntity("username", "password",
            null, null, null, null);
        userDao.save(user);

        TestUtils.setCurrentAuthenticatedUser(user);
    }

    public void updateQuestion_hp() {
        QuestionEntity question = createQuestion();

        String newBody = "body1";
        List<Tag> newTags = Arrays.asList(
            new Tag("tag1"), new Tag("tag2")
        );

        List<Long> newImageIds = Arrays.asList(createImage(), createImage())
            .stream()
            .map(ImageEntity::getId)
            .collect(Collectors.toList());

        CreateQuestionRequest request = new CreateQuestionRequest(newBody, newTags, newImageIds);

        QuestionWithComments updatedQuestionWithComments = questionService.updateQuestion(question.getId(), request);
        Question updatedQuestion = updatedQuestionWithComments.getQuestion();

        assertEquals(updatedQuestion.getBody(), newBody);

        List<String> expectedTagNames = newTags
            .stream()
            .map(Tag::getTagName)
            .collect(Collectors.toList());

        List<String> updatedQuestionTagNames = updatedQuestion.getTags()
            .stream()
            .map(Tag::getTagName)
            .collect(Collectors.toList());

        Collections.sort(expectedTagNames);
        Collections.sort(updatedQuestionTagNames);
        assertEquals(updatedQuestionTagNames, expectedTagNames);

        List<Long> updatedQuestionImageIds = updatedQuestion.getImageIds();
        Collections.sort(updatedQuestionImageIds);
        Collections.sort(newImageIds);
        assertEquals(updatedQuestionImageIds, newImageIds);
    }

    private QuestionEntity createQuestion() {
        QuestionEntity question = new QuestionEntity("body", user, Collections.emptyList(), Collections.emptyList());
        questionDao.save(question);
        return question;
    }

    private ImageEntity createImage() {
        ImageEntity image = new ImageEntity(new byte[]{ 0 }, "unknown");
        imageDao.save(image);
        return image;
    }
}
