package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.DataType;

public interface DataTypeFactory<T extends DataType<?>> {
    T create();
}
