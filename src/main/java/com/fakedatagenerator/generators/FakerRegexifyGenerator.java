package com.fakedatagenerator.generators;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.datafaker.Faker;

public class FakerRegexifyGenerator implements ValueGenerator {
  private final Faker faker;
  private final String regexString;

  public FakerRegexifyGenerator(Faker faker, String regexString) {
    this.faker = faker;
    this.regexString = checkRegexString(regexString);
  }

  @Override
  public Object nextValue() {
    return faker.regexify(this.regexString);
  }

  private String checkRegexString(String regexString) {
    try {
      Pattern.compile(regexString);
    } catch (PatternSyntaxException e) {
      throw new IllegalArgumentException("RegexString is not a valid regex string", e);
    }
    return regexString;
  }
}
