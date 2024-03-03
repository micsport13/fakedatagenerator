package com.fdg.fakedatagenerator.column;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

public class FakerMethodGenerator implements ValueGenerator {

  @Autowired private final Faker faker;
  private final String providerName;
  private final String methodName;
  private final Object[] methodArgs;

  public FakerMethodGenerator(
      Faker faker, String providerName, String methodName, Object... methodArgs) {
    this.faker = faker;
    this.providerName = providerName;
    this.methodName = methodName;
    this.methodArgs = methodArgs;
  }

  public Object nextValue() {
    return FakerMethodFactory.invokeMethod(faker, providerName, methodName, methodArgs);
  }
}
