package com.fdg.fakedatagenerator.constraints.column;

import com.fdg.fakedatagenerator.constraints.table.TableCheckConstraint;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.exceptions.CheckConstraintException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

/** Check Constraint Column Not to be confused with {@link TableCheckConstraint} */
@Getter
public final class ColumnCheckConstraint implements ColumnConstraint {
  private final Number min;
  private final Number
      max; // TODO: Figure out how to keep this generic yet converts to the datatype for precision
  private final Set<String> acceptedValues;

  private <T extends DataType<?>> ColumnCheckConstraint(Builder<T> builder) {
    this.min = builder.min;
    this.max = builder.max;
    this.acceptedValues = builder.acceptedValues;
  }

  /**
   * Checks if value passed obeys the check constraint
   *
   * @param value the value to be checked
   */
  @Override
  public void validate(Object value) {
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
    } else if (value instanceof String stringValue) {
      if (!acceptedValues.contains(stringValue)) {
        throw new CheckConstraintException("Value is not in the accepted list of values");
      }
    }
  }

  @Override
  public int hashCode() {
    int result = 17; // A prime number for initial value

    // Check and include the hash codes of non-null fields
    if (min != null) {
      result = 31 * result + min.hashCode();
    }
    if (max != null) {
      result = 31 * result + max.hashCode();
    }
    result = 31 * result + acceptedValues.hashCode();

    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ColumnCheckConstraint columnCheckConstraint)) {
      return false;
    }
    if (this.min != null
        && this.max != null
        && columnCheckConstraint.min != null
        && columnCheckConstraint.max != null) {
      return this.min.equals(columnCheckConstraint.min)
          && this.max.equals(columnCheckConstraint.max);
    }
    if (this.min != null && columnCheckConstraint.min != null) {
      if (!this.min.equals(columnCheckConstraint.min)) {
        return false;
      }
    }
    if (this.max != null && columnCheckConstraint.max != null) {
      return this.max.equals(columnCheckConstraint.max);
    }
    return this.acceptedValues.equals(columnCheckConstraint.acceptedValues);
  }

  @Override
  public String toString() {
    StringBuilder defaultString = new StringBuilder("Check: ");
    if (this.min != null && this.max != null) {
      return defaultString.toString() + this.min + " <= value <= " + this.max;
    }
    if (this.min != null) {
      return defaultString + "value >= " + this.min;
    }
    if (this.max != null) {
      return defaultString + "value <= " + this.max;
    }
    if (!this.acceptedValues.isEmpty()) {
      for (String value : this.acceptedValues) {
        defaultString.append(value).append(", ");
      }
    }
    return defaultString.toString();
  }

  @Override
  public boolean conflictsWith(ColumnConstraint other) {
    if (other instanceof ColumnCheckConstraint otherCheckConstraint) {

      if (otherCheckConstraint.getMin() != null && this.max != null) {
        return otherCheckConstraint.getMin().doubleValue() > this.max.doubleValue();
      }
      if (otherCheckConstraint.getMax() != null && this.max != null) {
        return otherCheckConstraint.getMax().doubleValue() < this.min.doubleValue();
      }
      if (otherCheckConstraint.getAcceptedValues() != null) {
        return !this.acceptedValues.equals(otherCheckConstraint.getAcceptedValues());
      }
    }
    return false;
  }

  /** The type Check constraint builder. */
  public static final class Builder<T extends DataType<?>> {
    private final T dataType;
    private final Set<String> acceptedValues = new HashSet<>();
    private Number
        min; // TODO: Figure out how to keep this generic yet converts to the datatype for precision
    private Number
        max; // TODO: Figure out how to keep this generic yet converts to the datatype for precision

    /** Instantiates a new Check constraint builder. */
    public Builder(T dataType) {
      this.dataType = dataType;
    }

    /**
     * With minimum value check constraint builder.
     *
     * @param minimumValue the minimum value
     * @return the check constraint builder
     */
    public <U extends Number> Builder<T> withMinimumValue(U minimumValue) {
      this.min = Objects.requireNonNull(minimumValue);
      return this;
    }

    /**
     * With maximum value check constraint builder.
     *
     * @param maximumValue the maximum value
     * @return the check constraint builder
     */
    public <U extends Number> Builder<T> withMaximumValue(U maximumValue) {
      this.max = Objects.requireNonNull(maximumValue);
      return this;
    }

    /**
     * With range check constraint builder.
     *
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @return the check constraint builder
     */
    public <U extends Number> Builder<T> withRange(U lowerBound, U upperBound) {
      this.min = Objects.requireNonNull(lowerBound);
      this.max = Objects.requireNonNull(upperBound);
      return this;
    }

    @SafeVarargs
    public final <U extends String> Builder<T> withAcceptedValues(
        U firstAcceptedValue, U... acceptedValues) {
      this.acceptedValues.add(Objects.requireNonNull(firstAcceptedValue));
      this.acceptedValues.addAll(List.of(acceptedValues));
      return this;
    }

    /**
     * Build column check constraint.
     *
     * @return the column check constraint
     */
    public ColumnCheckConstraint build() {
      if (!this.acceptedValues.isEmpty() && (this.min != null || this.max != null)) {
        throw new IllegalArgumentException("Only one of accepted values or range can be specified");
      }
      return new ColumnCheckConstraint(this);
    }
  }
}
