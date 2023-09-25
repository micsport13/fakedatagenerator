package Data.Column;


import Data.Exceptions.MismatchedDataTypeException;
import Data.Validators.ColumnValidators.ColumnValidator;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.OtherValidators.NameValidator;
import Data.Validators.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
 */
public class Column <T>{
    // TODO: Add static regex column name checking (Name validator?)

    private final String name;
    private final Class<T> dataType;
    //private final DataTypeValidator dataTypeValidator;
    private final Set<ColumnValidator> constraints;
    // TODO: Add default value option/ Determine where it needs to go

    /**
     * Instantiates a new Column with no constraints
     * @param columnName Name of the column
     * @param dataType Data type of the column
     */

    /**
     * Instantiates a new Column with no constraints
     *
     * @param columnName Name of the column
     * @param dataType   Data type of the column
     */
    public Column(String columnName, Class<T> dataType) {
        NameValidator.validate(columnName);
        this.name = columnName;
        this.dataType = dataType;
        this.constraints = new HashSet<>();
    }

    /**
     * Instantiates a new Column with constraints
     *
     * @param columnName  the column name
     * @param dataType    the column data type
     * @param constraints the constraints of the column, i.e {@link NotNullValidator}
     */
    public Column(String columnName, Class<T> dataType, ColumnValidator... constraints) {
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
    public Class<T> getDataType() {
        return dataType;
    }

    /**
     * Gets all column constraints.
     * Returns a defensive copy of the constraints
     *
     * @return the constraints of the column
     */
    public Set<ColumnValidator> getConstraints() {
        return new HashSet<>(this.constraints);
    }

    /**
     * Add constraint to the column.
     *
     * @param columnConstraint the column constraint
     */
    public void addConstraint(ColumnValidator columnConstraint) {
        this.constraints.add(columnConstraint);
    }

    /**
     * Checks if value is valid for the column
     *
     * @param value the object to be checked for insertion
     * @return true if the value is valid for the column
     */
    public boolean isValid(T value) {  // TODO: Should this be a boolean?
        for (Validator validator : this.constraints) {
            validator.validate(value);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() * this.constraints.hashCode() * this.dataType.hashCode();
    }

    /**
     * Checks if the column is equal to another column
     *
     * @param o The column object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column<?> column = (Column<?>) o;
        return this.name.equals(column.name) && this.dataType.equals(column.dataType);
    }

    /**
     * Prints out the column name, data type, and constraints in the following format:
     * Column:  name
     * Data Type:  data type
     * Constraints: constraint1, constraint2, etc.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType.getSimpleName());
        if (!this.constraints.isEmpty())
            for (ColumnValidator constraint : this.constraints) {
                string.append("\nValidator: ")
                        .append(constraint);
            }
        return string.toString();
    }
}
