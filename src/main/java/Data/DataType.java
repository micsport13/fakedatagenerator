package Data;

public enum DataType {
    VARCHAR(String.class),
    INT(Integer.class),
    BOOLEAN(Boolean.class),
    FLOAT(Float.class),
    DATETIME2(String.class);

    private final Class<?> associatedClass;

    DataType(Class<?> associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Class<?> getAssociatedClass() {
        return associatedClass;
    }
}