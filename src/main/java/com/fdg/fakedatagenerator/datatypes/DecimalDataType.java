package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public class DecimalDataType implements DataType<Double> {
    private final Integer precision;
    private final Integer scale;

    public DecimalDataType() {
        this.precision = 18; this.scale = 0;
    }

    public DecimalDataType(Integer precision, Integer scale) {
        this.precision = precision; this.scale = Objects.requireNonNullElse(scale, 0);
    }

    @Override
    public String serialize(Double value) {
        if (value == null) {
            return null;
        }
        // Format the double value with the specified precision and scale
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat format = new DecimalFormat(getFormatString(), symbols); return format.format(value);
    }

    @Override
    public Double deserialize(String value) throws DeserializationException {
        if (value == null) {
            return null;
        } try {
            // Parse the string back to a double value
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat format = new DecimalFormat(getFormatString(), symbols);
            format.setParseBigDecimal(true); // Handle precise decimals
            return format.parse(value).doubleValue();
        } catch (Exception e) {
            // Handle parsing errors
            throw new DeserializationException("Error deserializing decimal value: " + value);
        }
    }

    @Override
    public boolean isCompatible(String value) {
        try {
            // Parse the string back to a double value
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat format = new DecimalFormat(getFormatString(), symbols);
            format.setParseBigDecimal(true); // Handle precise decimals
            format.parse(value); return true;
        } catch (NumberFormatException | ParseException e) {
            // Handle parsing errors
            return false;
        }
    }

    private String getFormatString() {
        if (precision == null || scale == null || scale >= precision) {
            return "0"; // Default format, no precision or scale
        } return "#." + "0".repeat(Math.max(0, scale));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
