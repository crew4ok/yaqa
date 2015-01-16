package com.yaqa.web;

import com.yaqa.service.UserService;
import com.yaqa.web.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        userService.registerNewUser(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
