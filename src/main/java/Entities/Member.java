package Entities;

import Data.Column;
import Data.DataType;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class Member extends Entity {

    private Member(Builder builder) {
        super(builder);
    }

    public static class Builder extends EntityBuilder<Builder, Member> {

        /**
         * Default Member entity builder
         * If no parameters are passed, it will default to whatever is defined in this constructor
         * If you want to specify your own columns, use the other constructor
         */
        public Builder() {
            super();
            columnList.add(new Column.ColumnBuilder("Id", DataType.INT).withPrimaryKeyConstraint().build());
            columnList.add(new Column.ColumnBuilder("FirstName", DataType.VARCHAR).build());
            columnList.add(new Column.ColumnBuilder("LastName", DataType.VARCHAR).build());
            columnList.add(new Column.ColumnBuilder("Email", DataType.VARCHAR).build());
            columnList.add(new Column.ColumnBuilder("Phone", DataType.VARCHAR).build());
            columnList.add(new Column.ColumnBuilder("CreatedAt", DataType.DATETIME2).build());
            columnList.add(new Column.ColumnBuilder("UpdatedAt", DataType.DATETIME2).build());
        }

        /**
         * Member entity builder with custom columns
         * @param columnList A set of columns
         */
        public Builder(Set<Column> columnList) {
            super(columnList);
        }

        @Override
        public Member build() {
            return new Member(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
