package com.fakedatagenerator.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class SequentialValueGenerator implements ValueGenerator, StateValueGenerator {

  @Getter
  @JsonProperty("start")
  private final Integer startingValue;

  @Getter
  @JsonProperty("increment")
  private final Integer increment;

  private int counter = 0;

  SequentialValueGenerator(
      @JsonProperty("start") Integer startingValue, @JsonProperty("increment") Integer increment) {
    this.startingValue = startingValue;
    this.increment = increment;
  }

  @Override
  public synchronized Object nextValue() {
    Integer value = startingValue + (increment * counter);
    counter += 1;
    return value;
  }

  @Override
  public void reset() {
    this.counter = 0;
  }

  @Override
  public String toString() {
    return String.format("Sequence, Start: %d, Increment: %d", this.startingValue, this.increment);
  }
}
