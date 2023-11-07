package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecimalDataTypeTest {

    DecimalDataType decimalDataType;
    @BeforeEach
    public void setUp() {
        this.decimalDataType = new DecimalDataType();
    }

    @Test
    public void store_withValidInput_ThrowsNoException() {
        assertDoesNotThrow(() -> {
            this.decimalDataType.store(1.1);
        });
    }

    @Test
    public void store_withString_ThrowsNoException() {
        assertDoesNotThrow(() -> {
            this.decimalDataType.store("1.1");
        });
    }

    @Test
    public void store_withUncastableString_ThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> this.decimalDataType.store("test value"));
    }

    @Test
    public void store_withIntegerString_StoresWithCorrectPrecision() {
        String val = this.decimalDataType.store("1.00000001");
        assertEquals("1", val);
    }
}
