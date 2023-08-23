package Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

public enum DataType {
    BOOLEAN(Boolean.class),
    DATETIME2(ZonedDateTime.class),
    DATETIMEOFFSET(ZonedDateTime.class),
    FLOAT(Float.class),
    INT(Integer.class),
    VARCHAR(String.class);

    private final Class<?> associatedClass;


    DataType(Class<?> associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Class<?> getAssociatedClass() {
        return associatedClass;
    }
}