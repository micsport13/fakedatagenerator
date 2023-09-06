package Data.Column;

import Data.Constraint;
import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
 */
public class Column {

    private final String name;
    private final DataType dataType;
    private final Set<ColumnConstraint> constraints;

    /**
     * Instantiates a column without constraints
     * @param columnName
     * @param dataType
     */
     public Column(String columnName, DataType dataType) {
        this.name = columnName;
        this.dataType = dataType;
        this.constraints = new HashSet<>();
    }

    /**
     * Instantiates a new Column with constraints
     *
     * @param columnName  the column name
     * @param dataType    the column data type {@link DataType}
     * @param constraints the constraints of the column, i.e {@link NotNullConstraint}
     */
    public Column(String columnName, DataType dataType, ColumnConstraint... constraints) {
        this(columnName, dataType);
        this.constraints.addAll(List.of(Objects.requireNonNull(constraints, "Constraints cannot be null")));
    }

    /**
     * Gets name.
     *
     * @return the Column name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets data type.
     *
     * @return the data type of the column
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Gets all column constraints.
     * Returns a defensive copy of the constraints
     * @return the constraints of the column
     */
    public Set<ColumnConstraint> getConstraints() {
        return new HashSet<>(this.constraints);
    }

    /**
     * Add constraint to the column.
     *
     * @param columnConstraint the column constraint
     */
    public void addConstraint(ColumnConstraint columnConstraint) {
        this.constraints.add(columnConstraint);
    }

    /**
     * Prints out the column name, data type, and constraints in the following format:
     * Column:  name
     * Data Type:  data type
     * Constraints: constraint1, constraint2, etc.
     */
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

    /**
     * Checks if value is valid for the column
     *
     * @param value the object to be checked for insertion
     * @return true if the value is valid for the column
     * @throws MismatchedDataTypeException if the value is not of the correct data type
     */
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

    /**
     * Checks if the value is a valid float
     * Will try to convert strings to floats
     * {@link DataType#FLOAT}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     * @throws MismatchedDataTypeException If unable to convert to float, throws this exception
     */
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

    /**
     * Checks if the value is a valid datetimeoffset
     * {@link DataType#DATETIMEOFFSET}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
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

    /**
     * Checks if the value is a valid datetime2 object
     * Is a timestamp without a timezone
     * {@link DataType#DATETIME2}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
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

    /**
     * Checks if the value is a valid boolean
     * {@link DataType#BOOLEAN}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
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

    /**
     * Checks if the value is a valid integer
     * Note: Will convert strings to integers if possible
     * {@link DataType#INT}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
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

    /**
     * Checks if the value is a valid string
     * Note: Will not convert numbers to strings.  They must be passed as strings rather than integers and cast to strings
     * {@link DataType#VARCHAR}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
    private boolean isValidString(Object value) {
        if (value instanceof Number) {
            throw new MismatchedDataTypeException("Number values must be passed as strings rather than integers");
        }
        return true;
    }

    /**
     * Checks if the column is equal to another column
     * @param o The column object
     * @return
     */
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
