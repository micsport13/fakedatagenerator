package com.fakedatagenerator.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

@JsonDeserialize(using = FakerRegexifyGeneratorDeserializer.class)
public class FakerRegexifyGenerator implements ValueGenerator {
  private final Faker faker;
  private final String regexString;

  @Autowired
  public FakerRegexifyGenerator(Faker faker, @JsonProperty("regex") String regexString) {
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

  @Override
  public String toString() {
    return String.format("Regex: %s", this.regexString);
  }
}
