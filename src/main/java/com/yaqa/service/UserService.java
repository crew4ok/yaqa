package com.yaqa.service;

import com.yaqa.model.User;

public interface UserService {

    void registerNewUser(User user);

    User getCurrentAuthenticatedUser();
}
