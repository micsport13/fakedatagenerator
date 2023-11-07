package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class VarcharDataType implements DataType<String> {

    private final Integer maxLength;

    public VarcharDataType() {
        this.maxLength = 1;
    }
    public VarcharDataType(Integer maxLength) {
        this.maxLength = maxLength;
    }
    @Override
    public String store(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString().substring(0, this.maxLength);
    }

    @Override
    public String cast(Object value) {
        return value.toString().substring(0, this.maxLength);
    }

    @Override
    public boolean validate(String value) throws MismatchedDataTypeException {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
