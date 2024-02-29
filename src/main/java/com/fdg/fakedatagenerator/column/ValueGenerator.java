package com.fdg.fakedatagenerator.column;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@JsonSerialize(using = ValueGeneratorSerializer.class)
@JsonDeserialize(using = ValueGeneratorDeserializer.class)
public class ValueGenerator {

  @Autowired private final Faker faker;
  private final String providerName;
  private final String methodName;
  private final Object[] methodArgs;

  public ValueGenerator(Faker faker, String providerName, String methodName, Object... methodArgs) {
    this.faker = faker;
    this.providerName = providerName;
    this.methodName = methodName;
    this.methodArgs = methodArgs;
  }

  public Object get() {
    return FakerMethodFactory.invokeMethod(faker, providerName, methodName, methodArgs);
  }
}
