package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.column.Column;

public class IntegerDataType implements DataType<Integer> {
    @Override
    public String store(Integer value) {
        return value.toString();
    }

    @Override
    public Integer cast(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public boolean isCompatible(String value){
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
