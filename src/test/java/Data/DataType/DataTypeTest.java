package Data.DataType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Data type test.
 */
class DataTypeTest {
    /*
    TODO: Testing
        All data types have correct corresponding values
        Add additional types
        Add ability for specific database datatypes?
     */

    /**
     * Gets associated class.
     */

    @Test
    public void getAssociatedClass_ForEachValue_ReturnsCorrectClass() {
        assertTrue(DataType.INT.getAssociatedClass() == Integer.class);
        assertTrue(DataType.VARCHAR.getAssociatedClass() == String.class);
        assertTrue(DataType.FLOAT.getAssociatedClass() == Float.class);
        assertTrue(DataType.BOOLEAN.getAssociatedClass() == Boolean.class);
        assertTrue(DataType.DATETIME2.getAssociatedClass() == ZonedDateTime.class);
        assertTrue(DataType.DATETIMEOFFSET.getAssociatedClass() == ZonedDateTime.class);
    }
}