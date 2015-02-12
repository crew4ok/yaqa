package com.yaqa.service.impl;

import com.yaqa.dao.CommentDao;
import com.yaqa.dao.LikeDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.CommentEntity;
import com.yaqa.dao.entity.LikeEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.model.LikeResult;
import com.yaqa.model.User;
import com.yaqa.service.CommentService;
import com.yaqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public LikeResult likeComment(Long commentId) {
        final User currentAuthenticatedUser = userService.getCurrentAuthenticatedUser();
        final UserEntity currentUserEntity = userDao.getById(currentAuthenticatedUser.getId());

        final CommentEntity comment = commentDao.getById(commentId);

        final boolean likedByCurrentUser = currentUserEntity.getLikes()
                .stream()
                .anyMatch(l -> {
                    CommentEntity c = l.getComment();
                    return c != null && c.getId().equals(commentId);
                });

        LikeResult.Type likeType = LikeResult.Type.LIKE;
        if (!likedByCurrentUser) {
            likeComment(comment, currentUserEntity);
        } else {
            dislikeComment(comment, currentUserEntity);
            likeType = LikeResult.Type.DISLIKE;
        }

        return new LikeResult(commentDao.getLikeCount(comment), likeType);
    }

    private void likeComment(CommentEntity comment, UserEntity user) {
        final LikeEntity like = new LikeEntity(user, comment);
        likeDao.save(like);

        comment.getLikes().add(like);
        user.getLikes().add(like);

        commentDao.merge(comment);
        userDao.merge(user);
    }

    private void dislikeComment(CommentEntity comment, UserEntity user) {
        final LikeEntity like = comment.getLikes()
                .stream()
                .filter(l -> {
                    UserEntity liker = l.getLiker();
                    return liker != null && liker.getId().equals(user.getId());
                })
                .findFirst()
                .get();

        comment.getLikes().remove(like);
        user.getLikes().remove(like);

        commentDao.merge(comment);
        userDao.merge(user);

        likeDao.remove(like);
    }
}
