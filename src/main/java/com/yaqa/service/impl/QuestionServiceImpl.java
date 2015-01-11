package com.yaqa.service.impl;

import com.yaqa.dao.CommentDao;
import com.yaqa.dao.LikeDao;
import com.yaqa.dao.QuestionDao;
import com.yaqa.dao.TagDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.LikeEntity;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.exception.NotAnAuthorException;
import com.yaqa.model.Comment;
import com.yaqa.model.LikeResult;
import com.yaqa.model.Question;
import com.yaqa.model.QuestionWithComments;
import com.yaqa.model.Tag;
import com.yaqa.model.User;
import com.yaqa.service.QuestionService;
import com.yaqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Question> getAll() {
        return questionDao.getAll().stream()
                .map(Question::of)
                .collect(Collectors.toList());
    }

    @Override
    public Question getById(Long id) {
        return Question.of(questionDao.getById(id));
    }

    @Override
    public QuestionWithComments getByIdWithComments(Long id) {
        return QuestionWithComments.of(questionDao.getById(id));
    }

    @Override
    public List<Question> getByTagName(String tagName) {
        return questionDao.getByTagName(tagName).stream()
                .map(Question::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionWithComments createNewQuestion(Question question, List<Tag> tags) {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        final UserEntity currentUserEntity = userDao.getById(currentAuthenticatedUser.getId());

        final Map<Tag, TagEntity> existingEntities = tagDao.mapTagsToEntities(tags);
        final List<Tag> notExistingTags = tags
                .stream()
                .filter(t -> existingEntities.keySet().contains(t))
                .collect(Collectors.toList());

        final List<TagEntity> newTagEntities = notExistingTags.stream()
                .map(t -> {
                    TagEntity tagEntity = new TagEntity(t.getTagName());
                    tagDao.save(tagEntity);
                    return tagEntity;
                }).collect(Collectors.toList());

        newTagEntities.addAll(existingEntities.values());
        final QuestionEntity questionEntity = new QuestionEntity(
                question.getTitle(),
                question.getBody(),
                currentUserEntity,
                newTagEntities
        );

        questionDao.save(questionEntity);

        return QuestionWithComments.of(questionEntity);
    }

    @Override
    @Transactional
    public QuestionWithComments postComment(Long questionId, Comment comment) {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        final UserEntity currentUserEntity = userDao.getById(currentAuthenticatedUser.getId());

        final QuestionEntity questionEntity = questionDao.getById(questionId);

        final CommentEntity commentEntity = new CommentEntity(comment.getBody(), currentUserEntity, questionEntity);

        questionEntity.getComments().add(commentEntity);
        questionDao.merge(questionEntity);
        commentDao.save(commentEntity);

        return QuestionWithComments.of(questionEntity);
    }

    @Override
    @Transactional
    public QuestionWithComments editComment(Long commentId, Comment comment) {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        final UserEntity currentUserEntity = userDao.getById(currentAuthenticatedUser.getId());

        final CommentEntity commentEntity = commentDao.getById(commentId);

        if (!commentEntity.getAuthor().equals(currentUserEntity)) {
            throw new NotAnAuthorException();
        }

        commentEntity.setBody(comment.getBody());
        commentDao.merge(commentEntity);

        return QuestionWithComments.of(commentEntity.getQuestion());
    }

    @Override
    @Transactional
    public LikeResult likeQuestion(Long id) {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        final UserEntity currentUserEntity = userDao.getById(currentAuthenticatedUser.getId());

        final QuestionEntity question = questionDao.getById(id);

        final long userQuestionLikes = currentUserEntity.getLikes()
                .stream()
                .filter(l -> l.getQuestion().getId().equals(id))
                .count();

        LikeResult.Type likeType = LikeResult.Type.LIKE;
        if (userQuestionLikes == 0) {
            likeQuestion(question, currentUserEntity);
        } else {
            dislikeQuestion(question, currentUserEntity);
            likeType = LikeResult.Type.DISLIKE;
        }

        return new LikeResult(questionDao.getLikeCount(question), likeType);
    }

    private void likeQuestion(QuestionEntity question, UserEntity currentUserEntity) {
        final LikeEntity like = new LikeEntity(currentUserEntity, question);
        question.getLikes().add(like);

        questionDao.merge(question);
        likeDao.save(like);
    }

    private void dislikeQuestion(QuestionEntity question, UserEntity currentUserEntity) {
        final Map<Boolean, List<LikeEntity>> partitionedLikes = question.getLikes()
                .stream()
                .collect(Collectors.partitioningBy(l -> l.getLiker().equals(currentUserEntity)));

        final List<LikeEntity> likesToRemove = partitionedLikes.get(true);
        final List<LikeEntity> likesToRetain = partitionedLikes.get(false);

        question.setLikes(likesToRetain);
        questionDao.merge(question);
        likesToRemove.forEach(likeDao::remove);
    }
}
