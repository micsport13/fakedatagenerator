package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.FakerLetterifyGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerLetterifyValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerLetterifyValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    String letterifyString = (String) args.get("letterify_string");
    return new FakerLetterifyGenerator(faker, letterifyString);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of("letterify_string", Primitives.STRING);
  }
}
