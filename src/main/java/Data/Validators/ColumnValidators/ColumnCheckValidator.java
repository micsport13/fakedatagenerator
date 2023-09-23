package Data.Validators.ColumnValidators;

import Data.DataType.DataType;
import Data.Exceptions.CheckConstraintException;
import Data.Validators.TableValidators.TableCheckValidator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Check Validator Column
 * Not to be confused with {@link TableCheckValidator}
 */
public class ColumnCheckValidator implements ColumnValidator {
    private final DataType dataType;
    private final Number min;
    private final Number max;
    private final Set<String> acceptedValues;

    private ColumnCheckValidator(CheckConstraintBuilder builder) {
        this.dataType = builder.dataType;
        this.min = builder.min;
        this.max = builder.max;
        this.acceptedValues = builder.acceptedValues;
    }

    /**
     * Checks if value passed obeys the check constraint
     *
     * @param value the value to be checked
     */
    @Override
    public void validate(Object value) {
        if (value instanceof Integer) {
            double checkValue = ((Integer) value).doubleValue();
            if (min != null && checkValue < min.doubleValue()) {
                throw new CheckConstraintException("Value is below the minimum value allowed by the check constraint");
            }
            if (max != null && checkValue > max.doubleValue()) {
                throw new CheckConstraintException("Value is above the maximum value allowed by the check constraint");
            }
        } else if (value instanceof String stringValue) {
            if (!acceptedValues.contains(stringValue)) {
                throw new CheckConstraintException("Value is not in the accepted list of values");
            }
        }
    }

    @Override
    public int hashCode() {
        int result = 17; // A prime number for initial value

        // Check and include the hash codes of non-null fields
        if (min != null) {
            result = 31 * result + min.hashCode();
        }
        if (max != null) {
            result = 31 * result + max.hashCode();
        }
        result = 31 * result + acceptedValues.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ColumnCheckValidator columnCheckConstraint)) {
            return false;
        }
        if (this.min != null && this.max != null && columnCheckConstraint.min != null && columnCheckConstraint.max != null) {
            return this.min.equals(columnCheckConstraint.min) && this.max.equals(columnCheckConstraint.max);
        }
        if (this.min != null && columnCheckConstraint.min != null) {
            if (!this.min.equals(columnCheckConstraint.min)) {
                return false;
            }
        }
        if (this.max != null && columnCheckConstraint.max != null) {
            return this.max.equals(columnCheckConstraint.max);
        }
        return this.acceptedValues.equals(columnCheckConstraint.acceptedValues);
    }

    @Override
    public String toString() {
        StringBuilder defaultString = new StringBuilder("Check: ");
        if (this.min != null && this.max != null) {
            return defaultString.toString() + this.min + " <= value <= " + this.max;
        }
        if (this.min != null) {
            return defaultString + "value >= " + this.min;
        }
        if (this.max != null) {
            return defaultString + "value <= " + this.max;
        }
        if (!this.acceptedValues.isEmpty()) {
            for (String value : this.acceptedValues) {
                defaultString.append(value)
                        .append(", ");
            }
        }
        return defaultString.toString();
    }

    /**
     * The type Check constraint builder.
     */
    public static class CheckConstraintBuilder {
        private final DataType dataType;
        private final Set<String> acceptedValues = new HashSet<>();
        private Number min;
        private Number max;

        /**
         * Instantiates a new Check constraint builder.
         */
        public CheckConstraintBuilder(DataType dataType) {
            this.dataType = dataType;
        }

        /**
         * With minimum value check constraint builder.
         *
         * @param minimumValue the minimum value
         * @return the check constraint builder
         */
        public CheckConstraintBuilder withMinimumValue(Number minimumValue) {
            if (!Number.class.isAssignableFrom(this.dataType.getAssociatedClass())) {
                throw new IllegalArgumentException("Cannot set a minimum value on a non-numeric column");
            }
            this.min = Objects.requireNonNull(minimumValue);
            return this;
        }

        /**
         * With maximum value check constraint builder.
         *
         * @param maximumValue the maximum value
         * @return the check constraint builder
         */
        public CheckConstraintBuilder withMaximumValue(Number maximumValue) {
            if (!Number.class.isAssignableFrom(this.dataType.getAssociatedClass())) {
                throw new IllegalArgumentException("Cannot set a minimum value on a non-numeric column");
            }
            this.max = Objects.requireNonNull(maximumValue);
            return this;
        }


        /**
         * With range check constraint builder.
         *
         * @param lowerBound the lower bound
         * @param upperBound the upper bound
         * @return the check constraint builder
         */
        public CheckConstraintBuilder withRange(Number lowerBound, Number upperBound) {
            if (!Number.class.isAssignableFrom(this.dataType.getAssociatedClass())) {
                throw new IllegalArgumentException("Cannot assign ranges to non-numeric checks");
            }
            this.min = Objects.requireNonNull(lowerBound)
                    .doubleValue();
            this.max = Objects.requireNonNull(upperBound)
                    .doubleValue();
            return this;
        }

        public CheckConstraintBuilder withAcceptedValues(String... acceptedValues) {
            if (!String.class.isAssignableFrom(this.dataType.getAssociatedClass())) {
                throw new IllegalArgumentException("Cannot assign accepted string values to non-string type");
            }
            this.acceptedValues.addAll(Arrays.asList(Objects.requireNonNull(acceptedValues)));
            return this;
        }

        /**
         * Build column check constraint.
         *
         * @return the column check constraint
         */
        public ColumnCheckValidator build() {
            return new ColumnCheckValidator(this);
        }
    }
}
