package Entities;

import java.util.Set;

public class Column {
    private final DataType dataType;
    private Set<Constraint> constraints;
    public final String name;
    public Column(DataType dataType, String name) {
        this.dataType = dataType;
        this.name = name;
        this.constraints = null;
    }
    public Column(DataType dataType, String name, Set<Constraint> constraints) {
        this(dataType, name);
        this.constraints = constraints;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<Constraint> getConstraint() {
        return constraints;
    }

    public void setConstraint(Set<Constraint> constraint) {
        this.constraints = constraint;
    }
    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
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
