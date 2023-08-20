package Entities;

import Data.Column;
import Data.Constraint;
import Data.DataType;

import java.util.*;

public final class Member extends Entity {

    public Member(MemberBuilder memberBuilder){
        super(memberBuilder.columnSet, memberBuilder.values);
    }

    public static class MemberBuilder implements EntityBuilder<Member> {
        private Set<Column> columnSet = new LinkedHashSet<>();
        private Map<String, Object> values = new LinkedHashMap<>();

        @Override
        public EntityBuilder<Member> addExistingColumn(Column column) {
            return null;
        }

        @Override
        public EntityBuilder<Member> addColumn(DataType dataType, String name, Set<Constraint> constraints) {
            return null;
        }

        @Override
        public EntityBuilder<Member> setColumnValue(Column column, Object value) {
            return null;
        }

        @Override
        public Member build() {
            return new Member(this);
        }
    }
    @Override
    protected void setColumns(Set<Column> columnList) {
        this.columns = columnList;
    }

}
