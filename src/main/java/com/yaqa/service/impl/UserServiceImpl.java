package com.yaqa.service.impl;

import com.yaqa.dao.TagDao;
import com.yaqa.dao.UserDao;
import com.yaqa.dao.entity.TagEntity;
import com.yaqa.dao.entity.UserEntity;
import com.yaqa.exception.InvalidTagException;
import com.yaqa.exception.NotUniqueUsernameException;
import com.yaqa.model.Tag;
import com.yaqa.model.User;
import com.yaqa.service.UserService;
import com.yaqa.web.model.RegistrationRequest;
import com.yaqa.web.model.UpdateUserProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

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
            userDao.save(new UserEntity(
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getProfileImage()
            ));
        }
    }

    @Override
    @Transactional
    public User updateUser(UpdateUserProfileRequest request) {
        final User currentUser = getCurrentAuthenticatedUser();
        final UserEntity user = userDao.getById(currentUser.getId());

        final String newPassword = request.getPassword();
        final List<Tag> newTags = request.getTags();

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        if (newTags != null && !newTags.isEmpty()) {
            final Map<Tag, TagEntity> foundEntities = tagDao.mapTagsToEntities(newTags);

            if (foundEntities.size() != newTags.size()) {
                throw new InvalidTagException("All or some of the tags are invalid");
            }

            final Collection<TagEntity> tags = foundEntities.values();
            final List<TagEntity> tagsToAttach = tags.stream()
                    .filter(t -> !user.getSubscriptionTags().contains(t))
                    .collect(Collectors.toList());

            user.getSubscriptionTags().addAll(tagsToAttach);
        }

        userDao.save(user);

        return User.of(user);
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((org.springframework.security.core.userdetails.User) currentAuthentication.getPrincipal())
                .getUsername();

        return User.of(userDao.getByUsername(username));
    }
}
