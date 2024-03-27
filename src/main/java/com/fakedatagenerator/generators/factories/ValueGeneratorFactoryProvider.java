package com.fakedatagenerator.generators.factories;

import net.datafaker.Faker;

public class ValueGeneratorFactoryProvider {
  private final Faker faker;

  public ValueGeneratorFactoryProvider(Faker faker) {
      this.faker = faker;
  }
  public ValueGeneratorFactory getValueGeneratorFactory(String valueGeneratorTypeString) {
      return switch (valueGeneratorTypeString.toLowerCase()) {
          case "bothify" -> new FakerBothifyValueGeneratorFactory(faker);
          case "collection" -> new FakerCollectionValueGeneratorFactory(faker);
          case "expression" -> new FakerExpressionValueGeneratorFactory(faker);
          case "letterify" -> new FakerLetterifyValueGeneratorFactory(faker);
          case "method" -> new FakerMethodValueGeneratorFactory(faker);
          case "regexify" -> new FakerRegexifyValueGeneratorFactory(faker);
          case "templatify" -> new FakerTemplatifyValueGeneratorFactory(faker);
          case "foreign_value", "foreign_values" -> new FakerForeignValueGeneratorFactory(faker);
          case "sequence", "sequence_value" -> new FakerSequenceValueGeneratorFactory(faker);
          default -> throw new IllegalArgumentException("Unknown value generator type: " + valueGeneratorTypeString);
      };
  }
}
