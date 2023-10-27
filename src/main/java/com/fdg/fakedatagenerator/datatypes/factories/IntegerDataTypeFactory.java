package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.IntegerDataType;

public class IntegerDataTypeFactory implements DataTypeFactory<IntegerDataType> {
    @Override
    public IntegerDataType create() {
        return new IntegerDataType();
    }
}
