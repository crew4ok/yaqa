package com.yaqa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, 1 from users where username = ?")
                .authoritiesByUsernameQuery("select ?, 'ROLE_USER'");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))

                .and()
                .authorizeRequests()
                .antMatchers("/register/**", "/login").permitAll()
                .antMatchers("/question/**", "/user/**", "/tag/**", "/comment/**").hasRole("USER")

                .and()
                .formLogin()
                .successHandler((request, response, auth) -> response.setStatus(HttpServletResponse.SC_OK))
                .failureHandler((request, response, auth) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()
                .rememberMe()
        ;
    }


}
