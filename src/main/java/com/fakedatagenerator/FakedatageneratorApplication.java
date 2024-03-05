package com.fakedatagenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
@ComponentScan
public class FakedatageneratorApplication {
  public static void main(String[] args) {
    SpringApplication.run(FakedatageneratorApplication.class, args);
  }
}
