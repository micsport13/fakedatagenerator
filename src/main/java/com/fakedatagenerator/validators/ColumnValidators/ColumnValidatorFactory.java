package com.fakedatagenerator.validators.ColumnValidators;

import com.fakedatagenerator.validators.ConstraintType;

public class ColumnValidatorFactory {
    public static ColumnValidator createValidator(ConstraintType constraintType) {
        return switch(constraintType) {
            case CHECK -> throw new UnsupportedOperationException("When creating a column check constraint, you must provide parameters");
            case NOT_NULL -> new NotNullValidator();
            default -> null;
        };
    }

    public static <T extends Number> ColumnValidator createValidator(Class<T> dataType, T minValue, T maxValue) {
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
    public static <U extends String> ColumnValidator createValidator(U firstAcceptedValue, U... acceptedValues) {
        if (firstAcceptedValue == null) {
            throw new IllegalArgumentException("Must provide at least one acceptable value");
        }
        return new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues(firstAcceptedValue, acceptedValues).build();
    }


}
