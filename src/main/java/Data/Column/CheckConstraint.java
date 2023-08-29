package Data.Column;

import Entities.Entity;
import Entities.License;

import java.util.Set;

public class CheckConstraint implements ColumnConstraint{
    private final int min;
    private final int max;
    private Set<String> requiredValues;

    private CheckConstraint(Builder builder) {
        this.min = builder.min;
        this.max = builder.max;
        this.requiredValues = builder.requiredValues;
    }
    @Override
    public boolean isValid(Object value) {
        return true; // Todo: Add ability to define own check constraints
    }

    public static class CheckConstraintBuilder {
        private final int min;
        private final int max;
        private Set<String> requiredValues;
        public Builder() {
            super();
            String entityName = "License";
        }
        public Builder(Set<Column> columnList) {
            super(columnList);
        }

        @Override
        protected License.Builder self() {
            return this;
        }

        @Override
        public CheckConstraint build() {
            return new CheckConstraint(this);
        }
    }
}
