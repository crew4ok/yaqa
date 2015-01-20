package com.yaqa;

import com.yaqa.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class Bootstrap extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebConfig.class);
    }
}
