package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;


public interface DataType<T> { // TODO: Does this need types?
    String store(T value);

    T cast(String value) throws DeserializationException;

    boolean isCompatible(String value);
}
