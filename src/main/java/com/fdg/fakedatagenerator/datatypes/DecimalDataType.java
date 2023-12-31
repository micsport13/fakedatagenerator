package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fdg.fakedatagenerator.serializers.datatype.DecimalDataTypeSerializer;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonSerialize(using= DecimalDataTypeSerializer.class)
public class DecimalDataType implements DataType<BigDecimal> {
  @Getter
  private final Integer precision;
  @Getter
  private final Integer scale;
  @JsonIgnore
  private RoundingMode roundingMode;

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
  public String store(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    }
    try {
        BigDecimal decimalValue = new BigDecimal(value.toString());
        return decimalValue.setScale(this.scale, this.roundingMode).toString();
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
    return false;
    /*
    try {
      return false;
      // TODO: Fix this function
    } catch (NumberFormatException | ParseException e) {
      // Handle parsing errors
      return false;
    }*/
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof DecimalDataType decimalDataType) {
      return this.scale.equals(decimalDataType.getScale()) && this.precision.equals(((DecimalDataType) o).getPrecision());
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("Decimal(%1$d, %2$d)", this.precision, this.scale);
  }
}
