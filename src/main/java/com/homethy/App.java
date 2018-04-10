package com.homethy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableScheduling
//@MapperScan(value = "com.homethy.dao")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
