package com.delbot.danam.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  //

  @Bean
  public ModelMapper moedelMapper() {
    //
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
    .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
    .setFieldMatchingEnabled(true);
    return modelMapper;
  }
}
