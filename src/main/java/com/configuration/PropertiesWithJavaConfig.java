package com.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@PropertySource("classpath:application.properties")
@Configuration
public class PropertiesWithJavaConfig {
}
