package com.yaqa.config;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Test
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("test")
public @interface TestDaoConfig {
}
