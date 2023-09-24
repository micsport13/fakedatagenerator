package Data.Entities;

import Data.Column.Column;
import Data.DataType.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void entityConstructor_WithCorrectValues_ResultsInCorrectEntity(){
        Column idColumn = new Column("id", DataType.INT);
        Entity entity = new Entity.Builder(idColumn).withColumnValue("id",1).build();
        assertTrue((Integer) entity.getValue("id") == 1);
        assertTrue(entity.getColumnByName("id")==idColumn);
        assertTrue(entity.getColumns().size() == 1);
        assertTrue(entity.getColumnValueMapping().containsValue(1));
        assertTrue(entity.getColumnValueMapping().containsKey(idColumn));
    }
    @Test
    public void entityConstructor_WithNoProvidedColumnValues_ResultsInEntityWithNullInColumn() {
        Column idColumn = new Column("id", DataType.INT);
        Entity entity  = new Entity.Builder(idColumn).build();
        assertTrue(entity.getValue("id")==null);
        assertTrue(entity.getColumnValueMapping().get(idColumn) == null);
    }



}