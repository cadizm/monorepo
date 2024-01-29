package com.cadizm.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRest {

  @RequestMapping("/")
  public String hello() {
    return "Hello!";
  }
}
