package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;


public interface DataType<T> {
    String serialize(T value);

    T deserialize(String value) throws DeserializationException;

    boolean isCompatible(String value);
}
