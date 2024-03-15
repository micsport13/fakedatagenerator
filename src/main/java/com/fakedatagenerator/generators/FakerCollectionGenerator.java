package com.fakedatagenerator.generators;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.datafaker.Faker;

public class FakerCollectionGenerator implements ValueGenerator {
  @JsonIgnore private final Faker faker;
  private final List<?> collection;

  private FakerCollectionGenerator(Builder builder) {
    this.collection = builder.collection;
    this.faker = builder.faker;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Faker faker;
    private int minElements;
    private int maxElements;
    private double nullRate;
    private List<Supplier<?>> suppliers;
    private List<?> collection;

    public Builder(Faker faker) {
      this.faker = faker;
    }

    public Builder withFaker(Faker faker) {
      this.faker = faker;
      return this;
    }

    public Builder withSupplier(String provider, String method, Object... args) {
      this.suppliers.add(() -> FakerMethodFactory.getMethod(faker, provider, method, args));
      return this;
    }

    public Builder withLength(int minElements, int maxElements) {
      if (minElements < 0)
        throw new IllegalArgumentException("Min elements must be greater than 0");
      if (maxElements < minElements)
        throw new IllegalArgumentException("Max elements must be greater than min elements");

      this.minElements = minElements;
      this.maxElements = maxElements;
      return this;
    }

    public Builder withMaxLength(int maxElements) {
      if (maxElements < 0)
        throw new IllegalArgumentException("Max elements must be greater than 0");
      this.maxElements = maxElements;
      return this;
    }

    public Builder withNullRate(double nullRate) {
      if (nullRate < 0 || nullRate > 1)
        throw new IllegalArgumentException("Null rate must be between 0 and 1");
      this.nullRate = nullRate;
      return this;
    }

    public FakerCollectionGenerator build() {
      this.collection =
          faker.collection().len(minElements, maxElements).nullRate(nullRate).generate();
      return new FakerCollectionGenerator(this);
    }
  }

  @Override
  public Object nextValue() {
    return this.collection.get(faker.random().nextInt(this.collection.size()));
  }
}
