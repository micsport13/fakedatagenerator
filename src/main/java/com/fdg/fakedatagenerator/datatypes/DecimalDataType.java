package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.Getter;

@Getter
public class DecimalDataType implements DataType<BigDecimal> {
  @JsonProperty("precision")
  private final Integer precision;

  @JsonProperty("scale")
  final Integer scale;

  @JsonIgnore private RoundingMode roundingMode;

  public DecimalDataType() {
    this.precision = 18;
    this.scale = 0;
    this.roundingMode = RoundingMode.HALF_UP;
  }

  public DecimalDataType(Integer precision, Integer scale) {
    if (scale > precision) {
      throw new IllegalArgumentException("Scale cannot be greater than precision.");
    }
    this.precision = precision;
    this.scale = scale;
    this.roundingMode = RoundingMode.HALF_UP;
  }

  public DecimalDataType(Integer precision, Integer scale, RoundingMode roundingMode) {
    this(precision, scale);
    this.roundingMode = Objects.requireNonNullElse(roundingMode, RoundingMode.HALF_UP);
  }

  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    }
    try {
      return new BigDecimal(value.toString()).setScale(this.scale, this.roundingMode);
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("Error deserializing decimal value: " + value);
    }
  }

  @Override
  public BigDecimal cast(Object value) throws DeserializationException {
    if (value == null) {
      return null;
    } else {
      try {
        return new BigDecimal(value.toString()).setScale(this.scale, RoundingMode.HALF_UP);
      } catch (NumberFormatException e) {
        throw new DeserializationException("Error deserializing decimal value: " + value);
      }
    }
  }

  @Override
  public boolean validate(String value) {
    try {
      this.store(value);
    } catch (MismatchedDataTypeException e) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return this.precision.hashCode() * this.scale.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof DecimalDataType decimalDataType) {
      return this.scale.equals(decimalDataType.getScale())
          && this.precision.equals(((DecimalDataType) o).getPrecision());
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("Decimal(%1$d, %2$d)", this.precision, this.scale);
  }
}
