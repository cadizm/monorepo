package com.cadizm.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleMain {

  public static void main(String[] args) {
    System.out.println("SampleMain: Launching the sample SpringBoot demo application...");
    StringBuffer sb = new StringBuffer();
    for (String arg : args) {
      sb.append(arg);
      sb.append(" ");
    }

    System.out.println("SampleMain:  Command line args: " + sb);

    SpringApplication.run(SampleMain.class, args);
  }
}
