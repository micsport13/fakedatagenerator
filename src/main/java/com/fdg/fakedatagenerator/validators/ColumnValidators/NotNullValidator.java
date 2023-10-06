package com.fdg.fakedatagenerator.validators.ColumnValidators;

import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;

/**
 * The type Not null constraint.
 */

public class NotNullValidator implements com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator {
    @Override
    public void validate(Object value) {
        if (value == null) {
            throw new NotNullConstraintException(NotNullValidator.class.getSimpleName() + ": Value cannot be null");
        }
    }

    @Override
    public int hashCode() {
        return 79 * 31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof NotNullValidator;
    }

    @Override
    public String toString() {
        return "Not Null";
    }
}
