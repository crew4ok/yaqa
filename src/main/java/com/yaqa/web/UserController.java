package com.yaqa.web;

import com.yaqa.exception.ValidationException;
import com.yaqa.model.UserWithTags;
import com.yaqa.service.UserService;
import com.yaqa.web.model.UpdateUserProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserWithTags getUserById(@PathVariable("id") Long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public UserWithTags updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest request,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return userService.updateUser(request);
    }
}
