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
    public String store(String value) {
        return value;
    }

    @Override
    public String cast(String value) {
        return value.substring(0, this.maxLength);
    }

    @Override
    public boolean isCompatible(String value) throws MismatchedDataTypeException {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
