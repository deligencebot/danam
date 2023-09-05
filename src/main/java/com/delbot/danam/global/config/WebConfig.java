package com.delbot.danam.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
  //
  private String resourcePath = "/upload/**";
  @Value("${file.location}")
  private String savePath;
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //
    registry.addResourceHandler("/css/**", "/js/**", "/images/**", resourcePath)
            .addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/static/images/", savePath);
  }
}
