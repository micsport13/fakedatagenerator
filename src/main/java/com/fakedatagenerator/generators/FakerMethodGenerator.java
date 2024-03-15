package com.fakedatagenerator.generators;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

@JsonDeserialize(using = FakerMethodGeneratorDeserializer.class)
public class FakerMethodGenerator implements ValueGenerator {

  @JsonIgnore private final Faker faker;

  private final String providerName;

  private final String methodName;

  private final Object[] methodArgs;

  @Autowired
  public FakerMethodGenerator(
      Faker faker,
      @JsonProperty("provider") String providerName,
      @JsonProperty("method") String methodName,
      @JsonProperty("arguments") Object... methodArgs) {
    this.faker = faker;
    this.providerName = providerName;
    this.methodName = methodName;
    this.methodArgs = methodArgs;
  }

  public Object nextValue() {
    return FakerMethodFactory.invokeMethod(faker, providerName, methodName, methodArgs);
  }
}
