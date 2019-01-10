package com.arena.frontline.teststore.config;

import com.arena.frontline.teststore.utils.Mapper;
import com.arena.frontline.teststore.utils.MapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

  @Bean
  public Mapper beanMapper() {
    return new MapperImpl();
  }
}
