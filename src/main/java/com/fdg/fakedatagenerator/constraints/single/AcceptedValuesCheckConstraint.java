package com.fdg.fakedatagenerator.constraints.single;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.exceptions.CheckConstraintException;
import jakarta.validation.constraints.NotNull;
import java.util.*;

public class AcceptedValuesCheckConstraint implements ColumnLevelConstraint {
  @JsonProperty("acceptedValues")
  @JsonAlias("accepted_values")
  private final Set<String> acceptedValues;

  public AcceptedValuesCheckConstraint(@NotNull String... acceptedValues) {
    if (acceptedValues == null) {
      throw new IllegalArgumentException("Accepted values cannot be null");
    }
    if (acceptedValues.length == 0) {
      throw new IllegalArgumentException("Accepted values must have at least one value");
    }
    this.acceptedValues = new HashSet<>(List.of(acceptedValues));
  }

  @Override
  public void validate(Object value) throws RuntimeException {
    if (value instanceof String stringValue) {
      if (!acceptedValues.contains(stringValue)) {
        throw new CheckConstraintException("Value is not in the accepted list of values");
      }
    } else {
      throw new CheckConstraintException("Value is not in the accepted list of values");
    }
  }

  @Override
  public int hashCode() {
    return this.acceptedValues.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AcceptedValuesCheckConstraint that)) return false;
    return this.acceptedValues.equals(that.acceptedValues);
  }
}
