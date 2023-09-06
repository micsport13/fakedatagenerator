package Entities;

import Data.Column.Column;
import Data.DataType;
import Data.Exceptions.UniqueConstraintException;
import Data.TableConstraints.PrimaryKeyConstraint;
import Data.TableConstraints.UniqueConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Table test.
 */
class TableTest {
    private Table table;
    private Entity testEntity;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        this.table = new Table("TableTest");
        this.testEntity = new Entity.Builder(new Column("id", DataType.INT),
        new Column("name", DataType.VARCHAR)).withColumnValue("id",1).withColumnValue("name","Dave").build();
    }

    /**
     * Add member throws no exception.
     */
    @Test
    public void addMemberThrowsNoException() {
        this.testEntity.setColumnValue("id", 1);
        this.table.add(testEntity);
    }

    /**
     * Add multiple members throws no exception.
     */
    @Test
    public void addMultipleMembersThrowsNoException() {
        this.table.add(this.testEntity);
        this.table.add(this.testEntity);
    }

    /**
     * Add member with unique column throws no exception.
     */
    @Test
    public void addMemberWithUniqueColumnThrowsNoException() {
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueConstraint());
        this.table.add(this.testEntity);
    }

    /**
     * Add multiple members with unique values throws no exception.
     */
    @Test
    public void addMultipleMembersWithUniqueValuesThrowsNoException() {
        Entity testEntity2 = new Entity.Builder(new Column("id", DataType.INT),new Column("name", DataType.VARCHAR)).withColumnValue("id",2).withColumnValue("name","John").build();
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueConstraint());
        this.table.add(testEntity);
        Assertions.assertDoesNotThrow(() -> this.table.add(testEntity2));
    }

    /**
     * Add multiple members with non unique values throws exception.
     */
    @Test
    public void addMultipleMembersWithNonUniqueValuesThrowsException() {
        this.table.addTableConstraint(this.testEntity.getColumnByName("name"), new UniqueConstraint());
        this.table.add(this.testEntity);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.table.add(testEntity));
    }

}