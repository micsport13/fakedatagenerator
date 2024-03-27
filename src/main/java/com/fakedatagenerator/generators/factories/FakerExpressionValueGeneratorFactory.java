package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.FakerExpressionGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerExpressionValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerExpressionValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    String expressionString = (String) args.get("expression_string");
    String[] expressionExtras = (String[]) args.get("expression_extras");
    return new FakerExpressionGenerator(faker, expressionString, expressionExtras);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of(
        "expression_string", Primitives.STRING,
        "expression_extras",
            Primitives.STRING_ARRAY); // TODO: Figure out how to pass array of strings
  }
}
