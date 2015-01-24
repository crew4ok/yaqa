package com.yaqa.config.env;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDatabaseConfig {

    @Bean(name = "dbUrl")
    public String dbUrl() {
        return "jdbc:h2:mem:test";
    }

    @Bean(name = "dbUsername")
    public String dbUsername() {
        return "";
    }

    @Bean(name = "dbPassword")
    public String dbPassword() {
        return "";
    }
}
