package com.yaqa.service.impl;

import com.yaqa.dao.CommentDao;
import com.yaqa.dao.ImageDao;
import com.yaqa.dao.LikeDao;
import com.yaqa.dao.QuestionDao;
import com.yaqa.dao.TagDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.LikeEntity;
import com.yaqa.dao.entity.QuestionEntity;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.exception.InvalidImageIdException;
import com.yaqa.exception.NotAnAuthorException;
import com.yaqa.model.LikeResult;
import com.yaqa.model.Question;
import com.yaqa.model.QuestionWithComments;
import com.yaqa.model.Tag;
import com.yaqa.model.User;
import com.yaqa.service.ImageService;
import com.yaqa.service.QuestionService;
import com.yaqa.service.UserService;
import com.yaqa.web.model.CreateQuestionRequest;
import com.yaqa.web.model.PostCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private ImageDao imageDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Override
    public List<Question> getAll() {
        return questionDao.getAll().stream()
                .map(q -> Question.of(q, getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Override
    public Question getById(Long id) {
        return Question.of(questionDao.getById(id), getCurrentUser());
    }

    @Override
    public QuestionWithComments getByIdWithComments(Long id) {
        return QuestionWithComments.of(questionDao.getById(id), getCurrentUser());
    }

    @Override
    public List<Question> getByTagName(String tagName) {
        return questionDao.getByTagName(tagName).stream()
                .map(q -> Question.of(q, getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionWithComments createNewQuestion(CreateQuestionRequest request) {
        final UserEntity currentUser = getCurrentUser();

        // extract question tags
        List<TagEntity> questionTags = new ArrayList<>();
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            final Map<Tag, TagEntity> existingEntities = tagDao.mapTagsToEntities(request.getTags());
            final List<Tag> notExistingTags = request.getTags()
                    .stream()
                    .filter(t -> !existingEntities.keySet().contains(t))
                    .collect(Collectors.toList());

            final List<TagEntity> newTagEntities = notExistingTags.stream()
                    .map(t -> {
                        TagEntity tagEntity = new TagEntity(t.getTagName());
                        tagDao.save(tagEntity);
                        return tagEntity;
                    }).collect(Collectors.toList());

            questionTags.addAll(newTagEntities);
            questionTags.addAll(existingEntities.values());
        }

        // extract question images
        List<ImageEntity> images = new ArrayList<>();
        if (request.getImageIds() != null && !request.getImageIds().isEmpty()) {
            final List<ImageEntity> foundImages = imageDao.getByIds(request.getImageIds());

            if (foundImages.size() != request.getImageIds().size()) {
                throw new InvalidImageIdException("Some images were not found by provided id");
            }

            images.addAll(foundImages);
        }

        final QuestionEntity questionEntity = new QuestionEntity(
                request.getBody(),
                currentUser,
                questionTags,
                images
        );
        images.stream().forEach(i -> {
            i.setQuestion(questionEntity);
            imageDao.save(i);
        });

        questionDao.save(questionEntity);

        return QuestionWithComments.of(questionEntity, currentUser);
    }

    @Override
    @Transactional
    public QuestionWithComments postComment(Long questionId, PostCommentRequest request) {
        final UserEntity currentUser = getCurrentUser();

        final QuestionEntity questionEntity = questionDao.getById(questionId);

        List<ImageEntity> images = new ArrayList<>();
        if (request.getImageIds() != null && !request.getImageIds().isEmpty()) {
            final List<ImageEntity> foundImages = imageDao.getByIds(request.getImageIds());

            if (foundImages.size() != request.getImageIds().size()) {
                throw new InvalidImageIdException("Some images were not found by provided id");
            }

            images.addAll(foundImages);
        }

        final CommentEntity commentEntity = new CommentEntity(request.getBody(), currentUser, questionEntity, images);
        images.stream().forEach(i -> {
            i.setComment(commentEntity);
            imageDao.save(i);
        });

        questionEntity.getComments().add(commentEntity);
        questionDao.merge(questionEntity);
        commentDao.save(commentEntity);

        return QuestionWithComments.of(questionEntity, currentUser);
    }

    @Override
    @Transactional
    public QuestionWithComments editComment(Long commentId, PostCommentRequest request) {
        final UserEntity currentUser = getCurrentUser();

        final CommentEntity commentEntity = commentDao.getById(commentId);

        if (!commentEntity.getAuthor().equals(currentUser)) {
            throw new NotAnAuthorException();
        }

        if (request.getBody() != null && !request.getBody().isEmpty()) {
            commentEntity.setBody(request.getBody());
        }

        if (request.getImageIds() != null && !request.getImageIds().isEmpty()) {
            final List<ImageEntity> foundImages = imageDao.getByIds(request.getImageIds());

            if (foundImages.size() != request.getImageIds().size()) {
                throw new InvalidImageIdException("Some images were not found by provided id");
            }
            foundImages.stream().forEach(i -> {
                i.setComment(commentEntity);
                imageDao.save(i);
            });
            commentEntity.setImages(foundImages);
        }

        commentEntity.setBody(request.getBody());
        commentDao.merge(commentEntity);

        return QuestionWithComments.of(commentEntity.getQuestion(), currentUser);
    }

    @Override
    @Transactional
    public LikeResult likeQuestion(Long id) {
        final UserEntity currentUserEntity = getCurrentUser();

        final QuestionEntity question = questionDao.getById(id);

        final boolean currentUserLiked = currentUserEntity.getLikes()
                .stream()
                .anyMatch(l -> {
                    QuestionEntity q = l.getQuestion();
                    return q != null && q.getId().equals(id);
                });

        LikeResult.Type likeType = LikeResult.Type.LIKE;
        if (!currentUserLiked) {
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


    @Override
    public List<Question> getLastLimited(int limit) {

        return questionDao.getLastLimited(limit)
                .stream()
                .map(q -> Question.of(q, getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getBelowIdLimited(Long lastId, int limit) {
        return questionDao.getBelowIdLimited(lastId, limit)
                .stream()
                .map(q -> Question.of(q, getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getUserQuestionsLimited(int limit) {
        final UserEntity currentUserEntity = getCurrentUser();

        return questionDao.getByAuthorLimited(currentUserEntity, limit)
                .stream()
                .map(q -> Question.of(q, currentUserEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getUserQuestionsLimited(Long lastId, int limit) {
        final UserEntity currentUser = getCurrentUser();

        return questionDao.getByAuthorLimited(currentUser, lastId, limit)
                .stream()
                .map(q -> Question.of(q, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getCommentedByCurrentUserLimited(int limit) {
        final UserEntity currentUser = getCurrentUser();

        return questionDao.getCommentedByAuthorLimited(currentUser, limit)
                .stream()
                .map(q -> Question.of(q, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getCommentedByCurrentUserLimited(Long lastId, int limit) {
        final UserEntity currentUser = getCurrentUser();

        return questionDao.getCommentedByAuthorLimited(currentUser, lastId, limit)
                .stream()
                .map(q -> Question.of(q, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getUserSubscriptionLimited(int limit) {
        final UserEntity currentUser = getCurrentUser();

        return questionDao.getByUserTagsLimited(currentUser, limit)
                .stream()
                .map(q -> Question.of(q, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getUserSubscriptionLimited(Long lastId, int limit) {
        final UserEntity currentUser = getCurrentUser();
        final List<TagEntity> userTags = currentUser.getSubscriptionTags();

        return questionDao.getByUserTagsLimited(currentUser, lastId, limit)
                .stream()
                .map(q -> Question.of(q, currentUser))
                .collect(Collectors.toList());
    }

    private UserEntity getCurrentUser() {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        return userDao.getById(currentAuthenticatedUser.getId());
    }
}
