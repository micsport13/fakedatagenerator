package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.sql.Time;

public class TimeDataType implements DataType<Time> {
    @Override
    public Object store(Object value) throws MismatchedDataTypeException {
        return null;
    }

    @Override
    public Time cast(Object value) throws DeserializationException {
        return null;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }
}
