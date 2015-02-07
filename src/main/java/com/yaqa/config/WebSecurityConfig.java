package com.yaqa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaqa.model.User;
import com.yaqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
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
                .successHandler(successHandler())
                .failureHandler((request, response, auth) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()
                .headers().cacheControl()
                .disable()

                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(Integer.MAX_VALUE);
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        final JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        private static final ObjectMapper om = new ObjectMapper();

        @Autowired
        private UserService userService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            final User currentUser = userService.getCurrentAuthenticatedUser();
            response.getWriter().write(om.writeValueAsString(currentUser));
        }
    }

}
