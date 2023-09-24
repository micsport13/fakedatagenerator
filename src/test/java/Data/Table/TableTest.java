package Data.Table;

import Data.Column.Column;
import Data.DataType.DataType;
import Data.Entities.Entity;
import Data.Exceptions.UniqueConstraintException;
import Data.Validators.TableValidators.UniqueValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        this.testEntity = new Entity.Builder(new Column("id", DataType.INT),
                                             new Column("name", DataType.VARCHAR)).withColumnValue("id", 1)
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
        assertTrue(this.table.getEntities().size() == 2);
    }

    /**
     * Add member with unique column throws no exception.
     */
    @Test
    public void addTableConstraint_WithUniqueColumn_ThrowsNoException() {
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueValidator());
        this.table.add(this.testEntity);
    }

    /**
     * Add multiple members with unique values throws no exception.
     */
    @Test
    public void add_MultipleEntitiesOnUniqueColumn_ThrowsNoException() {
        Entity testEntity2 =
                new Entity.Builder(new Column("id", DataType.INT), new Column("name", DataType.VARCHAR)).withColumnValue("id", 2)
                        .withColumnValue("name", "John")
                        .build();
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueValidator());
        this.table.add(testEntity);
        Assertions.assertDoesNotThrow(() -> this.table.add(testEntity2));
    }

    /**
     * Add multiple members with non unique values throws exception.
     */
    @Test
    public void add_MultipleMembersWithNonUniqueIntoUniqueColumn_ValuesThrowsException() {
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueValidator());
        this.table.add(this.testEntity);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.table.add(testEntity));
    }

}