package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class DecimalDataType implements DataType<BigDecimal> {
  private final Integer precision;
  private final Integer scale;

  public DecimalDataType() {
    this.precision = 18;
    this.scale = 0;
  }

  public DecimalDataType(Integer precision, Integer scale) {
    if (scale > precision) {
      throw new IllegalArgumentException("Scale cannot be greater than precision.");
    }
    this.precision = precision;
    this.scale = Objects.requireNonNullElse(scale, 0);
  }

  @Override
  public String store(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    }
    try {
      // Parse the string back to a double value
      DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
      DecimalFormat format = new DecimalFormat(getFormatString(), symbols);
      format.setParseBigDecimal(true); // Handle precise decimals
      return format.parse(value.toString()).toString();
    } catch (Exception e) {
      throw new MismatchedDataTypeException("Error deserializing decimal value: " + value);
    }
  }

  @Override
  public BigDecimal cast(Object value) throws DeserializationException {
    if (value == null) {
      return null;
    }
    return new BigDecimal(value.toString());
    /*
    try {
      // TODO: Fix this function
    } catch (Exception e) {
      // Handle parsing errors
      throw new DeserializationException("Error deserializing decimal value: " + value);
    }*/
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

  private String getFormatString() {
    // TODO: Fix this function
    if (precision == null || scale == null || scale >= precision) {
      return "0"; // Default format, no precision or scale
    }
    return "#." + "0".repeat(Math.max(0, scale));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o != null && getClass() == o.getClass();
  }
}
