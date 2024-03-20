package com.fakedatagenerator.generators;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

public class FakerTemplatifyGenerator implements ValueGenerator {
  private final String stringToTemplate;
  private final char charToReplace;
  private final String[] replacementStrings;
  private final Faker faker;

  @Autowired
  public FakerTemplatifyGenerator(
      Faker faker, String stringToTemplate, char charToReplace, String... replacementStrings) {
    this.faker = faker;
    this.stringToTemplate = stringToTemplate;
    this.charToReplace = charToReplace;
    this.replacementStrings = replacementStrings;
  }

  @Override
  public Object nextValue() {
    return faker.templatify(this.stringToTemplate, this.charToReplace, this.replacementStrings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Templatify: ");
    sb.append("Template String: ");
    sb.append(this.stringToTemplate);
    sb.append(" Replacement Character: ");
    sb.append(this.charToReplace);
    sb.append(" Replacement Strings: ");
    for (String replacement : replacementStrings) {
      sb.append(replacement);
    }
    return sb.toString();
  }
}
