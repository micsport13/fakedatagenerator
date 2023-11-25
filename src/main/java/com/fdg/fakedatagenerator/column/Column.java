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
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Column class Contains a name, a data type, and a set of constraints
 */
@Getter
@JsonSerialize(using = ColumnSerializer.class)
@JsonDeserialize(using = ColumnDeserializer.class)
public class Column<T extends DataType<?>> {
    @JsonProperty("name")
    private final @NotNull String name;

    @JsonProperty("dataType")
    private final @NotNull T dataType;

    @JsonProperty("constraints")
    private final Set<ColumnConstraint> constraints = new HashSet<>();

    public Column(String columnName, T dataType) {
        NameValidator.validate(columnName);
        this.name = Objects.requireNonNull(columnName);
        this.dataType = Objects.requireNonNull(dataType);
    }

    public Column(
            String columnName, T dataType, @JsonProperty("constraints") ColumnConstraint... constraints) {
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

    public void validate(Object value) {
        for (Constraint constraint : this.constraints) {
            constraint.validate(
                    value);
        }
        this.getDataType()
                .store(value);
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
        StringBuilder string =
                new StringBuilder("Column: " + this.name + "\nData Type: " + this.dataType);
        if (!this.constraints.isEmpty()) {
            string.append("\nConstraint: ");
            for (ColumnConstraint constraint : this.constraints) {
                string.append(String.format("[%s]", constraint.toString()));
            }
        }
        return string.toString();
    }
}

