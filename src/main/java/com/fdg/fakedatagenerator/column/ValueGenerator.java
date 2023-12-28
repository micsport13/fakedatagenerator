package com.fdg.fakedatagenerator.column;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import net.datafaker.Faker;

public class ValueGenerator {
  @JsonProperty("package")
  private final String packageName;

  @JsonProperty("method")
  private final String methodName;

  @JsonIgnore private final Faker faker;
  @JsonIgnore private Method intermediateMethod;
  @JsonIgnore private final Method method;

  @JsonCreator
  public ValueGenerator(
      @JacksonInject Faker faker,
      @JsonProperty("package") String packageName,
      @JsonProperty("method") String methodName)
      throws InvocationTargetException, IllegalAccessException {
    this.packageName = packageName;
    this.methodName = methodName;
    this.faker = Objects.requireNonNullElseGet(faker, Faker::new);
    this.method = this.getFakeSupplier();
  }

  private Method getFakeSupplier() {
    Method[] methods = this.faker.getClass().getMethods();
    for (Method method : methods) {
      if (method.getName().equalsIgnoreCase(this.packageName)) {
        try {
          this.intermediateMethod = method;
          return method
              .getReturnType()
              .getMethod(this.methodName); // TODO: Make this case insensitive
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
      }
    }
    throw new IllegalArgumentException("Method does not exist");
  }

  public Object get()
      throws InvocationTargetException,
          IllegalAccessException,
          NoSuchMethodException,
          InstantiationException { // TODO: Catch these checked exceptions and throw a runtime in
                                   // its place
    return this.method.invoke(this.intermediateMethod.invoke(this.faker));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ValueGenerator that = (ValueGenerator) o;

    if (!packageName.equals(that.packageName)) return false;
    return methodName.equals(that.methodName);
  }

  @Override
  public int hashCode() {
    int result = packageName.hashCode();
    result = 31 * result + methodName.hashCode();
    return result;
  }
}
