package com.fdg.fakedatagenerator;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan(basePackages = "com.fdg.fakedatagenerator.commands")
public class FakedatageneratorApplication {

  Faker faker = new Faker();
  String name = faker.name().fullName(); // Miss Samanta Schmidt

  public static void main(String[] args) {
    SpringApplication.run(FakedatageneratorApplication.class, args);
  }
}
