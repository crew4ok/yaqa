package com.yaqa.service.impl;

import com.yaqa.dao.ImageDao;
import com.yaqa.dao.TagDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.exception.InvalidImageIdException;
import com.yaqa.exception.InvalidTagException;
import com.yaqa.exception.NotUniqueUsernameException;
import com.yaqa.model.Tag;
import com.yaqa.model.User;
import com.yaqa.model.UserWithTags;
import com.yaqa.service.UserService;
import com.yaqa.web.model.RegistrationRequest;
import com.yaqa.web.model.UpdateUserProfileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerNewUser(RegistrationRequest request) {
        try {
            userDao.getByUsername(request.getUsername());

            // if user exists, throw an exception
            throw new NotUniqueUsernameException("Username is not unique");
        } catch (EmptyResultDataAccessException e) {
            ImageEntity profileImage = null;
            if (request.getProfileImageId() != null) {
                try {
                    profileImage = imageDao.getById(request.getProfileImageId());
                } catch (EmptyResultDataAccessException e1) {
                    throw new InvalidImageIdException("Image was not found by id = " + request.getProfileImageId());
                }
            }

            final UserEntity userEntity = new UserEntity(
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    profileImage
            );
            if (profileImage != null) {
                profileImage.setUser(userEntity);
                imageDao.save(profileImage);
            }
            userDao.save(userEntity);
        }
    }

    @Override
    @Transactional
    public UserWithTags updateUser(UpdateUserProfileRequest request) {
        final User currentUser = getCurrentAuthenticatedUser();
        final UserEntity user = userDao.getById(currentUser.getId());

        final String newPassword = request.getPassword();
        final String firstName = request.getFirstName();
        final String lastName = request.getLastName();
        final String email = request.getEmail();
        final List<Tag> newTags = request.getTags();
        final Long newProfileImageId = request.getProfileImageId();

        // update password
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        // update firstName and lastName
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        }

        // update email
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }

        // update tags
        if (newTags != null && !newTags.isEmpty()) {
            final Map<Tag, TagEntity> foundEntities = tagDao.mapTagsToEntities(newTags);

            if (foundEntities.size() != newTags.size()) {
                throw new InvalidTagException("All or some of the tags are invalid");
            }

            final Collection<TagEntity> tags = foundEntities.values();
            user.setSubscriptionTags(new ArrayList<>(tags));
        }

        // update profile image
        if (newProfileImageId != null) {
            ImageEntity newProfileImage;
            try {
                newProfileImage = imageDao.getById(newProfileImageId);
            } catch (EmptyResultDataAccessException e) {
                throw new InvalidImageIdException("Image was not found by id = " + request.getProfileImageId());
            }
            if (newProfileImage != null) {
                newProfileImage.setUser(user);
                imageDao.save(newProfileImage);
            }

            user.setProfileImage(newProfileImage);
        }

        userDao.save(user);

        return UserWithTags.of(user);
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((org.springframework.security.core.userdetails.User) currentAuthentication.getPrincipal())
                .getUsername();

        return User.of(userDao.getByUsername(username));
    }

    @Override
    public UserWithTags getById(Long userId) {
        return UserWithTags.of(userDao.getById(userId));
    }
}
