package com.cadizm.springboot;

import org.springframework.context.annotation.Bean;

public class SampleAutoConfiguration {
  @Bean
  public String helloMessage() {
    return "Hello SpringBoot!";
  }
}
