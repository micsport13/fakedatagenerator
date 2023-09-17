package Data.Column;

import Data.DataType.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import Data.Validators.ColumnValidators.ColumnValidator;
import Data.Validators.DataTypeValidators.DataTypeValidatorFactory;
import Data.Validators.DataTypeValidators.DataTypeValidator;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
 */
public class Column {

    private final String name;
    private final DataType dataType;
    private final DataTypeValidator dataTypeValidator;
    private final Set<ColumnValidator> constraints;

    /**
     * Instantiates a column without constraints
     * @param columnName
     * @param dataType
     */
     public Column(String columnName, DataType dataType) {
        this.name = columnName;
        this.dataType = dataType;
        this.dataTypeValidator = DataTypeValidatorFactory.getValidator(dataType);
        this.constraints = new HashSet<>();
    }

    /**
     * Instantiates a new Column with constraints
     *
     * @param columnName  the column name
     * @param dataType    the column data type {@link DataType}
     * @param constraints the constraints of the column, i.e {@link NotNullValidator}
     */
    public Column(String columnName, DataType dataType, ColumnValidator... constraints) {
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
     * Prints out the column name, data type, and constraints in the following format:
     * Column:  name
     * Data Type:  data type
     * Constraints: constraint1, constraint2, etc.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (!this.constraints.isEmpty())
            for (ColumnValidator constraint : this.constraints) {
                string.append("\nValidator: ")
                        .append(constraint);
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
        for (Validator validator : this.constraints) {
            if (!validator.validate(value)) {
                return false;
            }
        }
        return this.dataTypeValidator.validate(value);
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
