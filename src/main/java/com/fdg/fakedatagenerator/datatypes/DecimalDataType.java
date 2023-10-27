package com.fdg.fakedatagenerator.datatypes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalDataType implements DataType<Double> {
    private final Integer precision;
    private final Integer scale;

    public DecimalDataType() {
        this.precision = 18;
        this.scale = 0;
    }
    public DecimalDataType(Integer precision, Integer scale) {
        this.precision = precision;
        this.scale = scale;
    }

    @Override
    public String serialize(Double value) {
        if (value == null) {
            return null;
        }

        // Format the double value with the specified precision and scale
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat format = new DecimalFormat(getFormatString(), symbols);
        return format.format(value);
    }

    @Override
    public Double deserialize(String value) {
        if (value == null) {
            return null;
        }

        try {
            // Parse the string back to a double value
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat format = new DecimalFormat(getFormatString(), symbols);
            format.setParseBigDecimal(true); // Handle precise decimals
            return format.parse(value).doubleValue();
        } catch (Exception e) {
            // Handle parsing errors
            return null;
        }
    }

    private String getFormatString() {
        if (precision == null || scale == null || scale >= precision) {
            return "0"; // Default format, no precision or scale
        }
        return "#." + "0".repeat(Math.max(0, scale));
    }
}
