package com.yaqa.config.env;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalDatabaseConfig {
    @Bean(name = "dbUsername")
    public String dbUsername() {
        return "yaqa";
    }

    @Bean(name = "dbPassword")
    public String dbPassword() {
        return "yaqa";
    }

    @Bean(name = "dbUrl")
    public String dbUrl() {
        return "jdbc:postgresql://localhost:5432/yaqa";
    }
}
