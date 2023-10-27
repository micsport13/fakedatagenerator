package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.VarcharDataType;

public class VarcharDataTypeFactory implements DataTypeFactory<VarcharDataType> {
    @Override
    public VarcharDataType create() {
        return new VarcharDataType();
    }
}
