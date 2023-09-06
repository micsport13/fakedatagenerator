package Data;

import java.time.ZonedDateTime;

/**
 * The enum Data type.
 */
public enum DataType {
    /**
     * Boolean data type.
     */
    BOOLEAN(Boolean.class),
    /**
     * Datetime 2 data type.
     */
    DATETIME2(ZonedDateTime.class),
    /**
     * Datetimeoffset data type.
     */
    DATETIMEOFFSET(ZonedDateTime.class),
    /**
     * Float data type.
     */
    FLOAT(Float.class),
    /**
     * Int data type.
     */
    INT(Integer.class),
    /**
     * Varchar data type.
     */
    VARCHAR(String.class);

    private final Class<?> associatedClass;


    DataType(Class<?> associatedClass) {
        this.associatedClass = associatedClass;
    }

    /**
     * Gets associated class.
     *
     * @return the associated class
     */
    public Class<?> getAssociatedClass() {
        return associatedClass;
    }
}