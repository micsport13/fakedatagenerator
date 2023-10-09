package com.fdg.fakedatagenerator.table;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.entities.Entity;
import com.fdg.fakedatagenerator.exceptions.UniqueConstraintException;
import com.fdg.fakedatagenerator.validators.TableValidators.UniqueValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Table test.
 */
class TableTest {
    /*
    TODO: Testing validations
        Check state (Correct columns, correct table constraints, name of table)
        Table name validation
        Check adding entities to make sure correct error is thrown.
        Check schema definition and schema order
     */
    private Table table;
    private Entity testEntity;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        this.table = new Table("TableTest");
        this.testEntity = new Entity.Builder(new Column<>("id", Integer.class),
                                             new Column<>("name", String.class)).withColumnValue("id", 1)
                .withColumnValue("name", "Dave")
                .build();
    }

    /**
     * Add member throws no exception.
     */
    @Test
    public void add_WithValidEntity_SuccessfullyAddedToTable() {
        this.testEntity.setColumnValue("id", 1);
        this.table.add(testEntity);
    }

    /**
     * Add multiple members throws no exception.
     */
    @Test
    public void add_WithMultipleEntities_AllAddedSucessfullyToTable() {
        this.table.add(this.testEntity);
        this.table.add(this.testEntity);
        assertEquals(2, this.table.getEntities()
                .size());
    }

    /**
     * Add member with unique column throws no exception.
     */
    @Test
    public void addTableConstraint_WithUniqueColumn_ThrowsNoException() {
        Optional<Column<?>> column = this.testEntity.getColumnByName("name");
        if (column.isPresent()) {
            this.table.addTableConstraint(column.get(), new UniqueValidator());
            this.table.add(this.testEntity);
        } else {
            Assertions.fail("Column not found");
        }
    }

    /**
     * Add multiple members with unique values throws no exception.
     */
    @Test
    public void add_MultipleEntitiesOnUniqueColumn_ThrowsNoException() {
        Entity testEntity2 =
                new Entity.Builder(new Column<>("id", Integer.class), new Column<>("name", String.class)).withColumnValue("id", 2)
                        .withColumnValue("name", "John")
                        .build();
        Optional<Column<?>> column = this.testEntity.getColumnByName("name");
        if (column.isPresent()) {
            this.table.addTableConstraint(column.get(), new UniqueValidator());
            this.table.add(testEntity);
            Assertions.assertDoesNotThrow(() -> this.table.add(testEntity2));
        } else {
            Assertions.fail("Column not found");
        }
    }

    /**
     * Add multiple members with non unique values throws exception.
     */
    @Test
    public void add_MultipleMembersWithNonUniqueIntoUniqueColumn_ValuesThrowsException() {
        Optional<Column<?>> column = this.testEntity.getColumnByName("name");
        if (column.isPresent()) {
            this.table.addTableConstraint(column.get(), new UniqueValidator());
            this.table.add(this.testEntity);
            Assertions.assertThrows(UniqueConstraintException.class, () -> this.table.add(testEntity));
        } else {
            Assertions.fail("Column not found");
        }
    }
}