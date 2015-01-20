package com.yaqa.service;

import com.yaqa.model.User;
import com.yaqa.model.UserWithTags;
import com.yaqa.web.model.RegistrationRequest;
import com.yaqa.web.model.UpdateUserProfileRequest;

public interface UserService {

    void registerNewUser(RegistrationRequest request);

    UserWithTags updateUser(UpdateUserProfileRequest request);

    User getCurrentAuthenticatedUser();

    UserWithTags getById(Long userId);
}
