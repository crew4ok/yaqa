package com.yaqa.config.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("heroku")
public class HerokuDatabaseConfig {
    @Value("#{ systemEnvironment['DATABASE_URL'] }")
    private String databaseUrl;

    @Bean(name = "dbUsername")
    public String dbUsername() {
        // TODO: rewrite (e.g. with regexp)
        return databaseUrl.split("//")[1].split(":")[0];
    }

    @Bean(name = "dbPassword")
    public String dbPassword() {
        // TODO: rewrite (e.g. with regexp)
        return databaseUrl.split("//")[1].split(":")[1].split("@")[0];
    }

    @Bean(name = "dbUrl")
    public String dbUrl() {
        return "jdbc:postgresql://" + databaseUrl.split("@")[1];
    }
}
