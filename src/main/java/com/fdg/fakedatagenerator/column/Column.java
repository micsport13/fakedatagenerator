package com.fdg.fakedatagenerator.column;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.other.NameValidator;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.serializers.column.ColumnDeserializer;
import com.fdg.fakedatagenerator.serializers.column.ColumnSerializer;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
 */
@Getter
@JsonSerialize(using = ColumnSerializer.class)
@JsonDeserialize(using = ColumnDeserializer.class)
public class Column<T extends DataType<?>> { // TODO: Does this need types?
    @JsonProperty("name")
    private final @NotNull String name;
    @JsonProperty("dataType")
    private final @NotNull T dataType;
    @JsonProperty("constraints")
    private final Set<ColumnConstraint> constraints = new HashSet<>();


    public Column(String columnName, T dataType) {
        NameValidator.validate(columnName);
        this.name = columnName;
        this.dataType = dataType;
    }

    public Column(String columnName, T dataType, @JsonProperty("constraints") ColumnConstraint... constraints) {
        this(columnName, dataType);
        for (ColumnConstraint constraint : constraints) {
            this.addConstraint(constraint);
        }
    }

    /**
     * Add constraint to the column.
     *
     * @param columnConstraint the column constraint
     */
    public void addConstraint(ColumnConstraint columnConstraint) {
        for (ColumnConstraint constraint : this.constraints) {
            if (constraint.conflictsWith(columnConstraint)) {
                throw new IllegalArgumentException("Constraint conflicts with existing constraints");
            }
        }
        this.constraints.add(columnConstraint);
    }


    public void validate(Object value) {  // TODO: Should this be a boolean?
        for (Constraint constraint : this.constraints) {
            constraint.validate(value); // TODO: this will no longer throw an exception because it violates LSP
        }
        this.getDataType().store(value);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() * this.constraints.hashCode() * this.dataType.hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column<?> column = (Column<?>) o;
        return this.name.equals(column.name) && this.dataType.equals(column.dataType);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (!this.constraints.isEmpty()) {
            for (ColumnConstraint constraint : this.constraints) {
                string.append("\nConstraint: ").append(constraint);
            }
        }
        return string.toString();
    }

}
