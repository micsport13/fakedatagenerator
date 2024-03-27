package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.FakerMethodGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerMethodValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerMethodValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    String providerName = (String) args.get("provider_name");
    String methodName = (String) args.get("method_name");
    Object[] methodArgs = (Object[]) args.get("method_args");
    return new FakerMethodGenerator(faker, providerName, methodName, methodArgs);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of(
        "provider_name",
        Primitives.STRING,
        "method_name",
        Primitives.STRING,
        "method_args",
        Primitives.OBJECT_ARRAY);
  }
}
