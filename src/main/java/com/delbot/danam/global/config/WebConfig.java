package com.delbot.danam.global.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer{
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**", "/js/**", "/images/**")
            .addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/static/images/");
  }
}
