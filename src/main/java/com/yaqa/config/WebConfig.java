package com.yaqa.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.yaqa.web")
@Import({
    DaoConfig.class,
    ServiceConfig.class,
    WebSecurityConfig.class
})
@EnableAutoConfiguration
public class WebConfig {
}
