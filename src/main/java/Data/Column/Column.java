package Data.Column;

import Data.Constraint;
import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;

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
        if (object.getClass() != this.dataType.getAssociatedClass()) {
            throw new MismatchedDataTypeException("Object is of type " + object.getClass().getSimpleName() + " and column requires " + this.dataType.getAssociatedClass());
        }
        this.values.add(object);
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
        if (Objects.requireNonNull(value, "Value cannot be null").getClass() != this.getDataType()
                .getAssociatedClass()) {
            throw new MismatchedDataTypeException("Value is of type " + value.getClass()
                    .getSimpleName() + " and column is of type " + this.getDataType()
                    .getAssociatedClass()
                    .getSimpleName());
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
