package Entities;

import Data.Column.Column;
import Data.DataType;

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
            String entityName = "Member";
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
