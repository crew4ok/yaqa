package com.yaqa.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@ComponentScan("com.yaqa.dao")
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
            System.out.println("activating local");
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

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
