package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.time.ZonedDateTime;

public class DateTimeOffsetDataType implements DataType<ZonedDateTime> {
    @Override
    public Object store(Object value) throws MismatchedDataTypeException {
        return null;
    }

    @Override
    public ZonedDateTime cast(Object value) throws DeserializationException {
        return null;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }
}
