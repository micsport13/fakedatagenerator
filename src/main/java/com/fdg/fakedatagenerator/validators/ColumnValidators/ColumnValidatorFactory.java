package com.fdg.fakedatagenerator.validators.ColumnValidators;

import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.validators.ColumnLevelConstraints;

public class ColumnValidatorFactory {

    public static ColumnValidator createValidator(ColumnLevelConstraints constraintType) {
        return switch (constraintType) {
            case CHECK ->
                    throw new IllegalArgumentException("When creating a column check constraint, you must provide parameters");
            case NOT_NULL -> new NotNullValidator();
            default -> throw new IllegalArgumentException("Invalid constraint type");
        };
    }

    public static <T extends Number> ColumnValidator createValidator(DataType<T> dataType, T minValue, T maxValue) {
        if (dataType == null) {
            throw new IllegalArgumentException("Data type cannot be null");
        }
        if (minValue != null && maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder<>(dataType).withRange(minValue, maxValue).build();
        }
        if (minValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder<>(dataType).withMinimumValue(minValue).build();
        }
        if (maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder<>(dataType).withMaximumValue(maxValue).build();
        }
        throw new IllegalArgumentException("Unable to create numeric check constraint");
    }

    @SafeVarargs
    public static <U extends String> ColumnValidator createValidator(U firstAcceptedValue, U... acceptedValues) { // TODO: Incorrectly assigning values, potentially revisit this constructor
        if (firstAcceptedValue == null) {
            throw new IllegalArgumentException("Must provide at least one acceptable value");
        }
        return new ColumnCheckValidator.CheckConstraintBuilder<>(new VarcharDataType()).withAcceptedValues(firstAcceptedValue, acceptedValues)
                                                                                     .build();
    }


}
