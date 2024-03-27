package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.Getter;

@Getter
public class DecimalDataType implements DataType<BigDecimal> {
  public static Integer DEFAULT_PRECISION = 18;
  public static Integer DEFAULT_SCALE = 0;
  public static RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

  @JsonProperty("precision")
  private final Integer precision;

  @JsonProperty("scale")
  final Integer scale;

  @JsonIgnore private RoundingMode roundingMode;

  public DecimalDataType() {
    this.precision = DEFAULT_PRECISION;
    this.scale = DEFAULT_SCALE;
    this.roundingMode = DEFAULT_ROUNDING_MODE;
  }

  public DecimalDataType(Integer precision, Integer scale) {
    if (scale > precision) {
      throw new IllegalArgumentException("Scale cannot be greater than precision.");
    }
    this.precision = precision;
    this.scale = scale;
    this.roundingMode = DEFAULT_ROUNDING_MODE;
  }

  public DecimalDataType(Integer precision, Integer scale, RoundingMode roundingMode) {
    this(precision, scale);
    this.roundingMode = Objects.requireNonNullElse(roundingMode, RoundingMode.HALF_UP);
  }

  @Override
  public BigDecimal cast(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    } else {
      try {
        return new BigDecimal(value.toString()).setScale(this.scale, RoundingMode.HALF_UP);
      } catch (NumberFormatException e) {
        throw new MismatchedDataTypeException("Error deserializing decimal value: " + value);
      }
    }
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
