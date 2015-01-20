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
        // FIXME: temporal hack to deploy on jelastic
        // should be replaced with more generic approach (e.g. JNDI)
        application.profiles("jelastic");
        return application.sources(WebConfig.class);
    }
}
