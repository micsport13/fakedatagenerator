package com.fdg.fakedatagenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan(basePackages = "com.fdg.fakedatagenerator.command")
@ComponentScan(basePackageClasses = {FakeDataGeneratorConfiguration.class})
public class FakedatageneratorApplication {
  public static void main(String[] args) {
    SpringApplication.run(FakedatageneratorApplication.class, args);
  }
}
