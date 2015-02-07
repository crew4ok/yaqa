package com.yaqa.util;

import com.yaqa.dao.entity.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class TestUtils {

    public static void setCurrentAuthenticatedUser(UserEntity userEntity) {
        final SecurityContext context = SecurityContextHolder.getContext();
        final User principal = new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
    }

}
