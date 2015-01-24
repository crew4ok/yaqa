package com.yaqa.config.env;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

@Configuration
@Profile("jelastic")
public class JelasticDatabaseConfig {
    private final Properties properties;

    public JelasticDatabaseConfig() throws IOException {
        final String userHome = System.getProperty("user.home");
        final Path path = FileSystems.getDefault().getPath(userHome, "db.properties");

        properties = new Properties();
        try (FileReader fileReader = new FileReader(path.toFile())) {
            properties.load(fileReader);
        }
    }

    @Bean(name = "dbUrl")
    public String dbUrl() {
        return properties.getProperty("db.url");
    }

    @Bean(name = "dbUsername")
    public String dbUsername() {
        return properties.getProperty("db.username");
    }

    @Bean(name = "dbPassword")
    public String dbPassword() {
        return properties.getProperty("db.password");
    }
}
