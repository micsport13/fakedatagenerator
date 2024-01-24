package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.math.BigInteger;

public class BigIntDataType implements DataType<BigInteger> {
    @Override
    public Object store(Object value) throws MismatchedDataTypeException {
        return null;
    }

    @Override
    public BigInteger cast(Object value) throws DeserializationException {
        return null;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }
}
