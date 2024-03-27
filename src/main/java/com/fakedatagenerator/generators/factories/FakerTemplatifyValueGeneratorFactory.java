package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.FakerTemplatifyGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerTemplatifyValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerTemplatifyValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    String stringToTemplate = (String) args.get("stringToTemplate");
    Character charToReplace = (Character) args.get("charToReplace");
    String[] replacementStrings = (String[]) args.get("charsToReplaceWith");
    return new FakerTemplatifyGenerator(faker, stringToTemplate, charToReplace, replacementStrings);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of(
        "stringToTemplate", Primitives.STRING,
        "charToReplace", Primitives.CHAR,
        "charsToReplaceWith", Primitives.STRING_ARRAY);
  }
}
