package Entities;

import Data.Column;
import Data.DataType;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class License extends Entity {
    /**
     * To build an entity, a list of columns and a mapping of the columns to their values is required.
     *
     * @param builder A set of columns
     */
    private License(Builder builder) {
        super(builder);
    }

    public static class Builder extends EntityBuilder<Builder, License> {

        public Builder() {
            columnList.add(new Column.ColumnBuilder("Id", DataType.INT).withPrimaryKeyConstraint().build());
            columnList.add(new Column.ColumnBuilder("Name", DataType.VARCHAR).build());
        }
        public Builder(Set<Column> columnList) {
            super(columnList);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public License build() {
            return new License(this);
        }
    }
}
