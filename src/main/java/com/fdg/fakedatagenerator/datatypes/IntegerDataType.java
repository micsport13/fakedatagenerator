package com.fdg.fakedatagenerator.datatypes;

import java.util.Objects;

public class IntegerDataType implements DataType<Integer> {
    @Override
    public String serialize(Integer value) {
        return value.toString();
    }

    @Override
    public Integer deserialize(String value) {
        return Integer.parseInt(value);
    }
}
