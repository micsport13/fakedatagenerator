package Data.Column;

import Data.Constraint;
import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Column {
    private String name;
    private final DataType dataType;
    private Set<ColumnConstraint> constraints;
    private List<Object> values = new ArrayList<>();

    public Column(String columnName, DataType dataType) {
        this.name = columnName;
        this.dataType = dataType;
        this.constraints = new HashSet<>();
    }
    public Column(String columnName, DataType dataType, ColumnConstraint... constraints) {
        this(columnName, dataType);
        this.constraints.addAll(List.of(Objects.requireNonNull(constraints, "Constraints cannot be null")));
    }

    public String getName() {
        return name;
    }

    public void setConstraints(Set<ColumnConstraint> constraints) {
        this.constraints = constraints;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<ColumnConstraint> getConstraints() {
        return constraints;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addConstraint(ColumnConstraint columnConstraint) {
        this.constraints.add(columnConstraint);
    }
    public void addValue(Object object) {
        if (object != null && object.getClass() != this.dataType.getAssociatedClass()) {
            throw new MismatchedDataTypeException("Object is of type " + object.getClass().getSimpleName() + " and column requires " + this.dataType.getAssociatedClass());
        }
        if (this.isValid(object)){
            this.values.add(object);
        }
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (this.constraints.isEmpty()) {
            string.append("\nConstraints: None");
        } else {
            for (ColumnConstraint constraint : this.constraints) {
                string.append("\nConstraint: ")
                        .append(constraint);
            }
        }
        return string.toString();
    }

    public boolean isValid(Object value) {
        for (Constraint constraint : this.constraints) {
            if (!constraint.isValid(value)) {
                return false;
            }
        }
        return switch (this.dataType) {
            case BOOLEAN -> this.isValidBoolean(value);
            case INT -> this.isValidInt(value);
            case VARCHAR -> this.isValidString(value);
            case DATETIME2 -> this.isValidDateTime2(value);
            case DATETIMEOFFSET -> this.isValidDateTimeOffset(value);
            case FLOAT -> this.isValidFloat(value);
            default -> throw new IllegalArgumentException("Data type not supported");
        };

    }

    private boolean isValidFloat(Object value) {
        return true;
    }

    private boolean isValidDateTimeOffset(Object value) {
        if (value instanceof ZonedDateTime) {
            return true;
        }
        if (value instanceof String) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) value);
                return true;

        }
        throw new MismatchedDataTypeException("Value must be a ZonedDateTime or a String");
    }

    private boolean isValidDateTime2(Object value) {
        if (value instanceof ZonedDateTime) {
            return true;
        }
        if (value instanceof String) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) value);
                return true;
            } catch (DateTimeParseException e) {
                throw new MismatchedDataTypeException("Date time string must be in the format of yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            }
        }
        throw new MismatchedDataTypeException("Value must be a ZonedDateTime or a String");
    }

    private boolean isValidBoolean(Object value) {
        if (value instanceof Integer) {
            if ((Integer) value != 0 && (Integer) value != 1) {
                throw new MismatchedDataTypeException("Boolean can only accept integer value of 0 or 1 or a boolean value");
            }
            return true;
        }
        if (!(value instanceof Boolean)) {
            throw new MismatchedDataTypeException("Value must be a boolean value or an integer value of 0 or 1");
        }
        return true;
    }
    private boolean isValidInt(Object value) {
        if (value instanceof Integer) {
            return true;
        }
        return value instanceof Double;
    }
    private boolean isValidString(Object value) {
        return value instanceof String;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Column column)) {
            return false;
        }
        return this.name.equals(column.name) && this.dataType.equals(column.dataType);
    }
    @Override
    public int hashCode() {
        return this.name.hashCode() * this.constraints.hashCode() * this.dataType.hashCode();
    }
}
