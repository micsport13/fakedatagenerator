package Data.Column;

import Data.Exceptions.CheckConstraintException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ColumnCheckConstraint implements ColumnConstraint{
    private final Double min;
    private final Double max;
    private final Set<String> acceptedValues;

    private ColumnCheckConstraint(CheckConstraintBuilder builder) {
        this.min = builder.min;
        this.max = builder.max;
        this.acceptedValues = builder.acceptedValues;
    }
    @Override
    public boolean isValid(Object value) {
        if (value instanceof Integer) {
            double checkValue = ((Integer) value).doubleValue();
            if (min != null && checkValue < min) {
                throw new CheckConstraintException("Value is below the minimum value allowed by the check constraint");
            }
            if (max != null && checkValue > max) {
                throw new CheckConstraintException("Value is above the maximum value allowed by the check constraint");
            }
        } else if (value instanceof String stringValue) {
            if (!acceptedValues.contains(stringValue)) {
                throw new CheckConstraintException("Value is not in the accepted list of values");
            }
        }

        return true;
    }
    public static class CheckConstraintBuilder {
        private Double min;
        private Double max;
        private final Set<String> acceptedValues = new HashSet<>();

        public CheckConstraintBuilder() {
        }

        public CheckConstraintBuilder withMinimumValue(Double minimumValue) {
            this.min = Objects.requireNonNull(minimumValue);
            return this;
        }
        public CheckConstraintBuilder withMinimumValue(Integer minimumValue) {
            this.min = Objects.requireNonNull(minimumValue).doubleValue();
            return this;
        }

        public CheckConstraintBuilder withMaximumValue(Double maximumValue) {
            this.max = Objects.requireNonNull(maximumValue);
            return this;
        }
        public CheckConstraintBuilder withMaximumValue(Integer maximumValue) {
            this.max = Objects.requireNonNull(maximumValue).doubleValue();
            return this;
        }


        public CheckConstraintBuilder withRange(Integer lowerBound, Integer upperBound) {
            this.min = Objects.requireNonNull(lowerBound).doubleValue();
            this.max = Objects.requireNonNull(upperBound).doubleValue();
            return this;
        }
        public CheckConstraintBuilder withRange(Double lowerBound, Double upperBound) {
            this.min = Objects.requireNonNull(lowerBound);
            this.max = Objects.requireNonNull(upperBound);
            return this;
        }

        public CheckConstraintBuilder withAcceptedValues(String... acceptedValues) {
            this.acceptedValues.addAll(Arrays.asList(Objects.requireNonNull(acceptedValues)));
            return this;
        }

        public ColumnCheckConstraint build() {
            return new ColumnCheckConstraint(this);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ColumnCheckConstraint columnCheckConstraint)) {
            return false;
        }
        if (this.min != null && this.max!= null && columnCheckConstraint.min != null && columnCheckConstraint.max != null) {
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
}
