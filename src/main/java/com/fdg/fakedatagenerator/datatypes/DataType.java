package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;


public interface DataType<T> { // TODO: Does this need types?
    String store(T value) throws MismatchedDataTypeException;

    T cast(String value) throws DeserializationException;

    boolean isCompatible(String value);
}
