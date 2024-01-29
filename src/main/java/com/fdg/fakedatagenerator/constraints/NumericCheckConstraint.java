package com.fdg.fakedatagenerator.constraints;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.exceptions.CheckConstraintException;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NumericCheckConstraint<T extends Number> implements Constraint {
  @JsonProperty("minValue")
  @JsonAlias("min_value")
  private final T min;

  @JsonProperty("maxValue")
  @JsonAlias("max_value")
  private final T max;

  private NumericCheckConstraint(Builder<T> builder) {
    this.min = builder.min;
    this.max = builder.max;
  }

  @Override
  public void validate(Object value) throws RuntimeException {
    if (Number.class.isAssignableFrom(value.getClass())) {
      Number checkValue = (Number) value;
      if (this.min != null && checkValue.doubleValue() < this.min.doubleValue()) {
        throw new CheckConstraintException(
            "Value is below the minimum value allowed by the check constraint");
      }
      if (this.max != null && checkValue.doubleValue() > this.max.doubleValue()) {
        throw new CheckConstraintException(
            "Value is above the maximum value allowed by the check constraint");
      }
      return;
    }
    throw new CheckConstraintException("Value is not a number");
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.min, this.max);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NumericCheckConstraint<?> that)) return false;
    return Objects.equals(this.min, that.min) && Objects.equals(this.max, that.max);
  }

  public static final class Builder<T extends Number> {
    @JsonProperty("minValue")
    @JsonAlias("min_value")
    private T min;

    @JsonProperty("maxValue")
    @JsonAlias("max_value")
    private T max;

    /**
     * With range check constraint builder.
     *
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @return the check constraint builder
     */
    public Builder<T> withRange(T lowerBound, T upperBound) {
      if (lowerBound == null && upperBound == null) {
        throw new IllegalArgumentException("One of the bounds must be set if calling this method");
      }
      if (lowerBound != null
          && upperBound != null
          && lowerBound.doubleValue() > upperBound.doubleValue()) {
        throw new IllegalArgumentException("Lower bound can not be greater than upper bound");
      }
      this.min = lowerBound;
      this.max = upperBound;
      return this;
    }

    /**
     * With minimum value check constraint builder.
     *
     * @param minimumValue the minimum value
     * @return the check constraint builder
     */
    public Builder<T> withMinimumValue(T minimumValue) {
      this.withRange(Objects.requireNonNull(minimumValue), null);
      return this;
    }

    /**
     * With maximum value check constraint builder.
     *
     * @param maximumValue the maximum value
     * @return the check constraint builder
     */
    public Builder<T> withMaximumValue(T maximumValue) {
      this.withRange(null, Objects.requireNonNull(maximumValue));
      return this;
    }

    /**
     * Build single check constraint.
     *
     * @return the single check constraint
     */
    public NumericCheckConstraint<T> build() {
      if (this.min == null && this.max == null) {
        throw new IllegalArgumentException("Numeric bounds must be set");
      }
      return new NumericCheckConstraint<>(this);
    }
  }
}
