package com.fdg.fakedatagenerator.column;

import net.datafaker.Faker;

public class FakerExpressionGenerator implements ValueGenerator {
  private final Faker faker;
  private final String expressionString;

  public FakerExpressionGenerator(
      Faker faker, String expressionString, String... expressionExtras) {
    this.faker = faker;
    this.expressionString = checkExpressionString(expressionString, expressionExtras);
  }

  @Override
  public Object nextValue() {
    return faker.expression(expressionString);
  }

  private String checkExpressionString(String expressionString, String... expressionExtras) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("#{%s", expressionString));
    for (String expressionExtra : expressionExtras) {
      sb.append(String.format(" '%s'", expressionExtra));
    }
    sb.append("}");
    return sb.toString();
  }
}
