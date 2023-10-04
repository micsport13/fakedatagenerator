package com.fakedatagenerator.validators.TableValidators;

import com.fakedatagenerator.table.Table;
import org.junit.jupiter.api.BeforeAll;

/**
 * The type Foreign key constraint test.
 */
class ForeignKeyConstraintTest {
    private ForeignKeyValidator foreignKeyValidator;
    private Table foreignTable;
    private String foreignColumnName;

    @BeforeAll
    void setUp() {
        this.foreignTable = new Table("foreignTable");

        this.foreignColumnName = "foreignColumn";
        this.foreignKeyValidator = new ForeignKeyValidator(foreignTable, foreignColumnName);
    }

}