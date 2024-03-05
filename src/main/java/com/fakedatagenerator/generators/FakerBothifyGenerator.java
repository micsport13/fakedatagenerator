package com.fakedatagenerator.generators;

import net.datafaker.Faker;

public class FakerBothifyGenerator implements ValueGenerator {
  private final Faker faker;
  private final String bothifyString;

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
}
