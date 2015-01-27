package com.yaqa.config;

import com.yaqa.config.env.HerokuDatabaseConfig;
import com.yaqa.config.env.JelasticDatabaseConfig;
import com.yaqa.config.env.LocalDatabaseConfig;
import com.yaqa.config.env.TestDatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.util.Properties;

@Configuration
@ComponentScan("com.yaqa.dao")
@Import(value = {
        HerokuDatabaseConfig.class,
        JelasticDatabaseConfig.class,
        LocalDatabaseConfig.class,
        TestDatabaseConfig.class
})
@DependsOn({"dbUrl", "dbUsername", "dbPassword"})
public class DaoConfig {
    private static final Logger log = LoggerFactory.getLogger(DaoConfig.class);

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
        log.info("DB Url: {}", dbUrl);
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
        config.setMaximumPoolSize(50);

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
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
