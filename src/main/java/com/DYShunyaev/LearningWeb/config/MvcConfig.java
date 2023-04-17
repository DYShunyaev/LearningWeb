package com.DYShunyaev.LearningWeb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + uploadPath + "/");
        registry.addResourceHandler("/logotip.png")
                .addResourceLocations("file:D:/Java/LearningWeb/logotip.png");
        registry.addResourceHandler("/logo.png")
                .addResourceLocations("file:D:/Java/LearningWeb/logo.png");
        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:" + uploadPath + "/");
        registry.addResourceHandler("/icons/**")
                .addResourceLocations("file:D:/Java/LearningWeb/icons/");
    }
}
