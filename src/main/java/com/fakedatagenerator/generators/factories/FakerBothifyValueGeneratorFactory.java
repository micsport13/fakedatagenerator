package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.FakerBothifyGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerBothifyValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerBothifyValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    String bothifyString = (String) args.get("bothify_string");
    return new FakerBothifyGenerator(faker, bothifyString);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of("bothify_string", Primitives.STRING);
  }
}
