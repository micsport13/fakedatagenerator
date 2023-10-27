package com.fdg.fakedatagenerator.datatypes;

public interface DataType<T> {
    String serialize(T value);

    T deserialize(String value);
}
