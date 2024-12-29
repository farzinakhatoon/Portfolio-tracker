package com.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Replace with the actual frontend URL (e.g., CodeSandbox or other
        // environments)
        registry.addMapping("/**").allowedOrigins("https://rpd2nf.csb.app/");
    }
}
