package Data;

import Data.Exceptions.InvalidForeignKeyException;

import java.util.HashSet;
import java.util.Set;

public class Column {
    private final DataType dataType;
    private final Set<Constraint> constraints;
    public final String name;
    public Column linkedColumn = null;

    public Column(ColumnBuilder columnBuilder) {
        this.dataType = columnBuilder.dataType;
        this.name = columnBuilder.name;
        this.constraints = new HashSet<>(columnBuilder.constraints);
        this.linkedColumn = columnBuilder.linkedColumn;
    }

    public static class ColumnBuilder {
        private final String name;
        private final DataType dataType;
        private final Set<Constraint> constraints = new HashSet<>();
        private Column linkedColumn;
        ColumnBuilder(String name, DataType dataType) {
            this.name = name;
            this.dataType = dataType;
        }
        public Column build() {
            return new Column(this);
        }
        public ColumnBuilder withUniqueConstraint() {
            this.constraints.add(Constraint.UNIQUE);
            return this;
        }
        public ColumnBuilder withNotNullConstraint() {
            this.constraints.add(Constraint.NOT_NULL);
            return this;
        }
        public ColumnBuilder withPrimaryKeyConstraint() {
            this.constraints.add(Constraint.PRIMARY_KEY);
            return this;
        }
        public ColumnBuilder withForeignKeyConstraint(Column linkedColumn) {
            if (linkedColumn == null) {
                throw new InvalidForeignKeyException("Linked column cannot be null");
            }
            this.constraints.add(Constraint.FOREIGN_KEY);
            this.linkedColumn = linkedColumn;
            return this;
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<Constraint> getConstraint() {
        return constraints;
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (this.constraints == null) {
            string.append("\nConstraints: None");
        }
        else {
            for (Constraint constraint : this.constraints) {
                string.append("\nConstraint: ").append(constraint);
            }
        }
        return string.toString();
    }
}
