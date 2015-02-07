package com.yaqa.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        StringBuilder cookieStr = new StringBuilder(cookies.length * 16);
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.getName())
                .append(": ")
                .append(cookie.getValue())
                .append(System.lineSeparator());
        }

        log.debug("Cookies in the request: {}", cookieStr);

        return true;
    }
}
