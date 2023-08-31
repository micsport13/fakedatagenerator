package Data.Column;

import Data.Exceptions.CheckConstraintException;

import java.util.Set;

public class CheckConstraint implements ColumnConstraint{
    private Double min;
    private Double max;
    private Set<String> acceptedValues;

    private CheckConstraint(CheckConstraintBuilder builder) {
        this.min = builder.min;
        this.max = builder.max;
        this.acceptedValues = builder.acceptedValues;
    }
    @Override
    public boolean isValid(Object value) {
        if (value instanceof Integer) {
            Double checkValue = ((Integer) value).doubleValue();
            if (min != null && checkValue < min) {
                throw new CheckConstraintException("Value is below the minimum value allowed by the check constraint");
            }

            if (max != null && checkValue > max) {
                throw new CheckConstraintException("Value is above the maximum value allowed by the check constraint");
            }
        } else if (value instanceof String) {
            String stringValue = (String) value;

            if (acceptedValues != null && !acceptedValues.contains(stringValue)) {
                throw new CheckConstraintException("Value is not in the accepted list of values");
            }
        }

        return true;
    }
    public static class CheckConstraintBuilder {
        private Double min;
        private Double max;
        private Set<String> acceptedValues;

        public CheckConstraintBuilder() {
        }

        public CheckConstraintBuilder withMinimumValue(Double minimumValue) {
            this.min = minimumValue;
            return this;
        }
        public CheckConstraintBuilder withMinimumValue(Integer minimumValue) {
            this.min = minimumValue.doubleValue();
            return this;
        }

        public CheckConstraintBuilder withMaximumValue(Double maximumValue) {
            this.max = maximumValue;
            return this;
        }
        public CheckConstraintBuilder withMaximumValue(Integer maximumValue) {
            this.max = maximumValue.doubleValue();
            return this;
        }


        public CheckConstraintBuilder withRange(Integer lowerBound, Integer upperBound) {
            this.min = lowerBound.doubleValue();
            this.max = upperBound.doubleValue();
            return this;
        }
        public CheckConstraintBuilder withRange(Double lowerBound, Double upperBound) {
            this.min = lowerBound;
            this.max = upperBound;
            return this;
        }

        public CheckConstraintBuilder withAcceptedValues(String... acceptedValues) {
            for (String value : acceptedValues) {
                this.acceptedValues.add(value);
            }
            return this;
        }

        public CheckConstraint build() {
            return new CheckConstraint(this);
        }
    }
}
