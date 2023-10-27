package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;

public class DecimalDataTypeFactory implements DataTypeFactory<DecimalDataType> {
    @Override
    public DecimalDataType create() {
        return new DecimalDataType();
    }
}
