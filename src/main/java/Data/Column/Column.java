package Data.Column;

import Data.Constraint;
import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Column {
    private final String name;
    private final DataType dataType;
    private final Set<ColumnConstraint> constraints;

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
        this.constraints.addAll(constraints);
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<ColumnConstraint> getConstraints() {
        return new HashSet<>(this.constraints);
    }

    public void addConstraint(ColumnConstraint columnConstraint) {
        this.constraints.add(columnConstraint);
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
        };

    }

    private boolean isValidFloat(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            try{
                Double doubleValue = Double.parseDouble((String) value);
                return true;
            } catch (NumberFormatException e) {
                throw new MismatchedDataTypeException("Value must be a floating point number or an integer for this column");
            }
        }
        throw new MismatchedDataTypeException("Value must be a floating point number or an integer for this column");
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
            ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) value);
            return true;
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
        if (value instanceof Number) {
            return true;
        }
        else if (value instanceof String) {
            try {
                Integer integer = Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new MismatchedDataTypeException("Value must be an integer for this column");
            }
            return true;
        }
        throw new MismatchedDataTypeException("Value must be an integer for this column");
    }
    private boolean isValidString(Object value) {
        if (value instanceof Number) {
            throw new MismatchedDataTypeException("Number values must be passed as strings rather than integers");
        }
        return true;
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
