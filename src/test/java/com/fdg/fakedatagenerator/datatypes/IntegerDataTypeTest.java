package com.fdg.fakedatagenerator.datatypes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDataTypeTest {

    IntegerDataType integerDataType;
    @BeforeEach
    public void setUp() {
        this.integerDataType = new IntegerDataType();
    }

    @Test
    public void store_withValidInput_ThrowsNoException() {
        assertDoesNotThrow(() -> {
            this.integerDataType.store(1);
        });
    }
    @Test
    public void store_parseableString_ThrowsNoException() {
        assertDoesNotThrow(() -> {
            this.integerDataType.store("1");
        });
    }

    @Test
    public void cast_parseableString_ReturnsIntegerValue() {
        assertEquals(1, this.integerDataType.cast("1"));
    }
    @Test
    public void cast_parseableStringFraction_ReturnsLowerBoundedIntegerValue() {
        assertEquals(1, this.integerDataType.cast("1.5"));
    }

    @Test
    public void cast_double_ReturnsIntegerValue() {
        assertEquals(1, this.integerDataType.cast(1.0));
    }

    @Test
    public void cast_doubleWithFractionalValue_ReturnsLowerBoundedIntegerValue() {
        assertEquals(1, this.integerDataType.cast(1.5));
    }

    @Test
    public void cast_floatWithFractionalValue_ReturnsLowerBoundedIntegerValue() {
        assertEquals(1, this.integerDataType.cast(1.5f));
    }
}
