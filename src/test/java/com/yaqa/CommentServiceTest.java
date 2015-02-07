package com.yaqa;

import com.yaqa.config.DaoConfig;
import com.yaqa.config.ServiceConfig;
import com.yaqa.dao.CommentDao;
import com.yaqa.dao.QuestionDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.model.LikeResult;
import com.yaqa.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

@Test
@ContextConfiguration(classes = {DaoConfig.class, ServiceConfig.class})
@ActiveProfiles("test")
public class CommentServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    private CommentEntity commentEntity;

    @BeforeMethod
    public void createComment() {
        final UserEntity user = new UserEntity("username", "password", null, null, null, null);
        userDao.save(user);

        final SecurityContext context = SecurityContextHolder.getContext();
        final User principal = new User(user.getUsername(), user.getPassword(), Collections.emptyList());
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));

        final QuestionEntity question = new QuestionEntity("body", user, Collections.emptyList(), Collections.emptyList());
        questionDao.save(question);

        final CommentEntity comment = new CommentEntity("body", user, question, Collections.emptyList());
        commentDao.save(comment);

        commentEntity = comment;
    }

    @Test(threadPoolSize = 10)
    public void testCommentLike() {
        final LikeResult likeResult = commentService.likeComment(commentEntity.getId());
        assertEquals(likeResult.getLikesCount(), Long.valueOf(1));
        assertEquals(likeResult.getType(), LikeResult.Type.LIKE);

        final LikeResult dislikeResult = commentService.likeComment(commentEntity.getId());
        assertEquals(dislikeResult.getLikesCount(), Long.valueOf(0));
        assertEquals(dislikeResult.getType(), LikeResult.Type.DISLIKE);
    }
}
