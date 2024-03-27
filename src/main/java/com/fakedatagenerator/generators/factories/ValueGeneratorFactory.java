package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.FactoryOptions;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;

public interface ValueGeneratorFactory extends FactoryOptions {
  ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args);
}
