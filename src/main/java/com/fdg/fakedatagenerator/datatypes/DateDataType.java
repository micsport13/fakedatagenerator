package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.util.Date;

public class DateDataType implements DataType<Date> {
    @Override
    public Object store(Object value) throws MismatchedDataTypeException {
        return null;
    }

    @Override
    public Date cast(Object value) throws DeserializationException {
        return null;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }
}
