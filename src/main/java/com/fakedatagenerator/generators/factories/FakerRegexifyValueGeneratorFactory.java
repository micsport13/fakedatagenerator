package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerRegexifyValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerRegexifyValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    return null;
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return null;
  }
}
