package com.yaqa.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.yaqa.web")
@Import({
    DaoConfig.class,
    ServiceConfig.class,
    WebSecurityConfig.class
})
@EnableAutoConfiguration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final Properties cacheMappings = new Properties();
        cacheMappings.setProperty("/image/**", "60");

        final WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheMappings(cacheMappings);

        registry.addInterceptor(webContentInterceptor);
    }
}
