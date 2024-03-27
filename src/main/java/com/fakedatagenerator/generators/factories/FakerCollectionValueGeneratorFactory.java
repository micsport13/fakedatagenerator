package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerCollectionValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerCollectionValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    throw new UnsupportedOperationException();
  }
}
