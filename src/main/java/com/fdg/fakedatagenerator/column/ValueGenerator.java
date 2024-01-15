package com.fdg.fakedatagenerator.column;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;

@Log4j2
public class ValueGenerator {
  // TODO: Figure out how to incorporate any function from faker
  @JsonProperty("package")
  private final String packageName;

  @JsonProperty("method")
  private final String methodName;

  @JsonIgnore private final Faker faker;
  @JsonIgnore private Method intermediateMethod;
  @JsonIgnore private final Method method;

  // TODO: Change this to a Builder and use json builder instead
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
          log.error(e);
          throw new RuntimeException(e);
        }
      }
    }
    throw new IllegalArgumentException("Method does not exist");
  }

  public Object get() {
    try {
      return this.method.invoke(this.intermediateMethod.invoke(this.faker));
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
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
//
//  public static class Builder { // TODO: Make this builder to return a final method to call
//
//    @Autowired private Faker faker; // TODO: Does this need to be here?
//    private Method method;
//
//    private Class<?> clazz;
//
//    public Builder(Faker faker, String clazzName) {
//      try {
//        this.clazz = faker.getClass().getMethod(clazzName).getReturnType();
//      } catch (NoSuchMethodException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//    public Builder withSubclass(String clazzName) {
//      try {
//        this.clazz = this.clazz.getMethod(clazzName).getReturnType();
//      } catch (NoSuchMethodException e) {
//        throw new RuntimeException(e);
//      }
//      return this;
//    }
//
//    public Builder withMethod(String methodName) {
//      try {
//        this.method = this.clazz.getMethod(methodName);
//      } catch (NoSuchMethodException e) {
//        throw new RuntimeException(e);
//      }
//      return this;
//    }
//
//    public ValueGenerator build() {
//      return new ValueGenerator(this.faker, this.clazz, this.method);
//    }
//  }
}
