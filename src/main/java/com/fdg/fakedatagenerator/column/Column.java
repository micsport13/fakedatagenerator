package com.fdg.fakedatagenerator.column;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.serializers.column.ColumnDeserializer;
import com.fdg.fakedatagenerator.serializers.column.ColumnSerializer;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fdg.fakedatagenerator.validators.OtherValidators.NameValidator;
import com.fdg.fakedatagenerator.validators.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
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
    private final Set<ColumnValidator> constraints = new HashSet<>();


    public Column(String columnName, T dataType) {
        NameValidator.validate(columnName);
        this.name = columnName;
        this.dataType = dataType;
    }

    public Column(String columnName, T dataType, @JsonProperty("constraints") ColumnValidator... constraints) {
        this(columnName, dataType);
        for (ColumnValidator constraint : constraints) {
            this.addConstraint(constraint);
        }
    }

    /**
     * Add constraint to the column.
     *
     * @param columnConstraint the column constraint
     */
    public void addConstraint(ColumnValidator columnConstraint) {
        for (ColumnValidator constraint : this.constraints) {
            if (constraint.conflictsWith(columnConstraint)) {
                throw new IllegalArgumentException("Constraint conflicts with existing constraints");
            }
        }
        this.constraints.add(columnConstraint);
    }


    public boolean isValid(T value) {  // TODO: Should this be a boolean?
        for (Validator validator : this.constraints) {
            validator.validate(value); // TODO: this will no longer throw an exception because it violates LSP
        }
        return true;
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
            for (ColumnValidator constraint : this.constraints) {
                string.append("\nValidator: ").append(constraint);
            }
        }
        return string.toString();
    }

}
