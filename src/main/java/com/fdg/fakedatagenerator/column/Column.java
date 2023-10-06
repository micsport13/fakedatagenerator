package com.fdg.fakedatagenerator.column;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.serializers.ColumnDeserializer;
import com.fdg.fakedatagenerator.serializers.ColumnSerializer;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fdg.fakedatagenerator.validators.ColumnValidators.NotNullValidator;
import com.fdg.fakedatagenerator.validators.OtherValidators.NameValidator;
import com.fdg.fakedatagenerator.validators.Validator;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Column class
 * Contains a name, a data type, and a set of constraints
 */
@Getter
@JsonSerialize(using = ColumnSerializer.class)
@JsonDeserialize(using = ColumnDeserializer.class)
public class Column <T> {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("dataType")
    private final Class<T> dataType;
    @JsonProperty("constraints")
    private final Set<ColumnValidator> constraints = new HashSet<>();
    // TODO: Add default value option/ Determine where it needs to go

    // TODO: Add deserializer

    /**
     * Instantiates a new Column with no constraints
     *
     * @param columnName Name of the column
     * @param dataType   Data type of the column
     */
    public Column(String columnName,  Class<T> dataType) {
        NameValidator.validate(columnName);
        this.name = columnName;
        this.dataType = dataType;
    }

    /**
     * Instantiates a new Column with constraints
     *
     * @param columnName  the column name
     * @param dataType    the column data type
     * @param constraints the constraints of the column, i.e {@link NotNullValidator}
     */
    public Column(String columnName, Class<T> dataType, @JsonProperty("constraints") ColumnValidator... constraints) {
        this(columnName, dataType);
        this.constraints.addAll(List.of(Objects.requireNonNull(constraints, "Constraints cannot be null")));
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
