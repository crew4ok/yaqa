package com.yaqa.service;

import com.yaqa.model.User;
import com.yaqa.web.model.RegistrationRequest;
import com.yaqa.web.model.UpdateUserProfileRequest;

public interface UserService {

    void registerNewUser(RegistrationRequest request);

    User updateUser(Long userId, UpdateUserProfileRequest request);

    User getCurrentAuthenticatedUser();
}
