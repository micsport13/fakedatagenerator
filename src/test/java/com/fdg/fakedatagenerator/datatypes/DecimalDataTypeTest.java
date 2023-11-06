package com.fdg.fakedatagenerator.datatypes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class DecimalDataTypeTest {

    DecimalDataType decimalDataType;
    @BeforeEach
    public void setUp() {
        this.decimalDataType = new DecimalDataType();
    }

    public void store_withValidInput_ThrowsNoException() {
        assertDoesNotThrow(() -> {
            this.decimalDataType.store(1.1);
        });
    }
}
