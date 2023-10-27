package com.fdg.fakedatagenerator.datatypes;

public class VarcharDataType implements DataType<String>{

    private final Integer maxLength;

    public VarcharDataType() {
        this.maxLength = 1;
    }
    public VarcharDataType(Integer maxLength) {
        this.maxLength = maxLength;
    }
    @Override
    public String serialize(String value) {
        return value;
    }

    @Override
    public String deserialize(String value) {
        return value.substring(0, this.maxLength);
    }
}
