package com.fakedatagenerator.generators;

import net.datafaker.Faker;

public class FakerLetterifyGenerator implements ValueGenerator {
  private final Faker faker;
  private final String letterifyString;

  public FakerLetterifyGenerator(Faker faker, String letterifyString) {
    this.faker = faker;
    this.letterifyString = letterifyString;
  }

  @Override
  public Object nextValue() {
    return faker.letterify(letterifyString);
  }

  private String checkLetterifyString(String letterifyString) {
    if (!letterifyString.contains("?")) {
      throw new IllegalArgumentException(
          "Letterify string does not have ? as replacement character");
    }
    return letterifyString;
  }
}
