package Data;

import Data.Exceptions.MismatchedDataTypeException;
import Data.Exceptions.NotNullConstraintException;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Column {
    public final String entityName;
    public final String name;
    private final DataType dataType;
    private final Set<ColumnConstraint> constraints;
    public Map<Column, Object> linkedColumn = null; // Should come from a collection of entities

    private Column(ColumnBuilder columnBuilder) {
        this.dataType = columnBuilder.dataType;
        this.entityName = columnBuilder.entityName;
        this.name = columnBuilder.name;
        this.constraints = columnBuilder.constraints;
        this.linkedColumn = columnBuilder.linkedColumn;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<ColumnConstraint.Constraints> getConstraints() {
        return constraints;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (this.constraints.isEmpty()) {
            string.append("\nConstraints: None");
        } else {
            for (ColumnConstraint.Constraints constraint : this.constraints) {
                string.append("\nConstraint: ")
                        .append(constraint);
            }
        }
        return string.toString();
    }

    public boolean isValid(Object value) {
        if (value == null && this.constraints.contains(ColumnConstraint.Constraints.NOT_NULL)) {
            throw new NotNullConstraintException("Value cannot be null");
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

    public static class ColumnBuilder {
        private final String entityName;
        private final String name;
        private final DataType dataType;
        private final Set<ColumnConstraint> constraints = new HashSet<ColumnConstraint>();
        private Column linkedColumn = null;

        /**
         * Required parameters for a column
         *
         * @param name       Name of the column
         * @param entityName Name of the object to which the column belongs
         * @param dataType   Data type of the column
         */
        public ColumnBuilder(String name, String entityName, DataType dataType) {
            this.name = name;
            this.entityName = entityName;
            this.dataType = dataType;
        }

        public ColumnBuilder withCheckConstraint() {
            this.constraints.add(new ColumnConstraint(ColumnConstraint.Constraints.CHECK));
            return this;
        }

        public ColumnBuilder withNotNullConstraint() {
            this.constraints.add(new ColumnConstraint(ColumnConstraint.Constraints.NOT_NULL));
            return this;
        }

        public Column build() {
            return new Column(this);
        }
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
