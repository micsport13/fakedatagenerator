package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class IntegerDataType implements DataType<Integer> {


    @Override
    public String store(Object value) throws MismatchedDataTypeException {
        if (value == null) {
            return null;
        }
        try {
            int intValue = Integer.parseInt(value.toString());
            return Integer.toString(intValue);
        } catch (NumberFormatException e) {
            throw new MismatchedDataTypeException("Error casting integer value: " + value);
        }
    }

    @Override
    public Integer cast(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.valueOf((String) value);
            } catch (NumberFormatException e) {
                throw new DeserializationException("Error deserializing integer value: " + value);
            }
        }
        throw new DeserializationException("Error deserializing integer value: " + value);
    }

    @Override
    public boolean validate(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
