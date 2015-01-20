package com.yaqa.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

@Configuration
@ComponentScan("com.yaqa.dao")
@DependsOn({"dbUrl", "dbUsername", "dbPassword"})
public class DaoConfig {

    @Configuration
    @Profile("heroku")
    public static class HerokuDatabaseConfig {
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

    @Configuration
    @Profile("local")
    public static class LocalDatabaseConfig {
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

    @Configuration
    @Profile("test")
    public static class TestDatabaseConfig {

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

    @Configuration
    @Profile("jelastic")
    public static class JelasticDatabaseConfig {
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

    @Autowired
    @Qualifier("dbUsername")
    private String dbUsername;

    @Autowired
    @Qualifier("dbPassword")
    private String dbPassword;

    @Autowired
    @Qualifier("dbUrl")
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        // a little hack to help DriverManager to load proper driver from classpath
        try {
            Class.forName("org.postgresql.ds.PGSimpleDataSource");
        } catch (ClassNotFoundException e) {
            // NOP
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);

        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws MalformedURLException {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.yaqa.dao.entity");

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("show_sql", "true");
        jpaProperties.setProperty("format_sql", "true");
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
