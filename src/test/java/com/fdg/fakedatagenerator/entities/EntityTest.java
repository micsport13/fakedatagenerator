package com.fdg.fakedatagenerator.entities;

import com.fdg.fakedatagenerator.column.Column;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Entity test.
 */
class EntityTest {
    /* TODO: Things to test
        Selecting Entity values
        Inserting Entity values
        Updating Entity values
        Deleting Entity values
     */
    @Test
    public void entityConstructor_WithCorrectValues_ResultsInCorrectEntity() {
        Column<Integer> idColumn = new Column<>("id", Integer.class);
        Entity entity = new Entity.Builder(idColumn).withColumnValue("id", 1)
                .build();
        int id = (int) entity.getValue("id")
                .orElseThrow(NoSuchElementException::new);
        assertEquals(1, id);
        assertSame(entity.getColumnByName("id")
                           .orElseThrow(NoSuchElementException::new), idColumn);
        assertEquals(1, entity.getColumns()
                .size());
        assertTrue(entity.getColumnValueMapping()
                           .getMap()
                           .containsValue(1));
        assertTrue(entity.getColumnValueMapping()
                           .getMap()
                           .containsKey(idColumn));
    }

    @Test
    public void entityConstructor_WithNoProvidedColumnValues_ResultsInEntityWithNullInColumn() {
        Column<Integer> idColumn = new Column<>("id", Integer.class);
        Entity entity = new Entity.Builder(idColumn).build();
        assertTrue(entity.getValue("id")
                           .isEmpty());
        assertNull(entity.getColumnValueMapping()
                           .get(idColumn));
    }


}