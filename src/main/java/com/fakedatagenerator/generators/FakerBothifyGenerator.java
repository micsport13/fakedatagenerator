package com.fakedatagenerator.generators;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

public class FakerBothifyGenerator implements ValueGenerator {
  @JsonIgnore private final Faker faker;
  private final String bothifyString;

  @Autowired
  public FakerBothifyGenerator(Faker faker, String bothifyString) {
    this.faker = faker;
    this.bothifyString = checkBothifyString(bothifyString);
  }

  @Override
  public Object nextValue() {
    return faker.bothify(bothifyString);
  }

  private String checkBothifyString(String bothifyString) {
    if (!bothifyString.contains("?") || !bothifyString.contains("#")) {
      throw new IllegalArgumentException(
          "Bothify string doesn't contain any replacement characters");
    }
    return bothifyString;
  }

  @Override
  public String toString() {
    return String.format("Bothify: %s", bothifyString);
  }
}
