package Entities;
import Data.Column;
import Data.Constraint;
import Data.Exceptions.InvalidForeignKeyException;
import Data.Exceptions.InvalidPrimaryKeyException;
import Data.Exceptions.InvalidUniquessException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemberFactory implements EntityFactory<Member> {
    private final Set<Object> primaryKeys = new HashSet<>();
    private final Map<Column,Set<Object>> foreignKeys = new HashMap<>();
    private final Set<Object> uniqueKeys = new HashSet<>();
    public Member createEntity(Set<Column> values) {
            Member member = new Member(values);
            if (this.isValidEntity(member)) {
                this.updateKeySets(member);
            }
            return member;
    }
    @Override
    public boolean isValidEntity(Member entity) {
        for (Map.Entry<Column, Object> entry : entity.getColumnValueMapping().entrySet()){
            if (entry.getKey().getConstraint() != null) {
                if (entry.getKey().getConstraint().contains(Constraint.PRIMARY_KEY) && this.primaryKeys.contains(entry.getValue())) {
                    throw new InvalidPrimaryKeyException("Contains a value that violates primary key constraint");
                }
                if (entry.getKey().getConstraint().contains(Constraint.UNIQUE) && this.uniqueKeys.contains(entry.getValue())) {
                    throw new InvalidUniquessException("Contains a value that violates unique key constraint");
                }
                if (entry.getKey().getConstraint().contains(Constraint.FOREIGN_KEY) && !this.foreignKeys.get(entry.getKey()).contains(entry.getValue())) {
                    throw new InvalidForeignKeyException("Contains a value that violates foreign key constraint");
                }
            }
        }
        return true;
    }

    @Override
    public void addForeignKeyValue(Column column, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateKeySets(Member member) {
        for (Column column : member.getColumns()) {
            if (column.getConstraint() != null) {
                Object columnValue = member.getColumnValueMapping().get(column);
                if (column.getConstraint().contains(Constraint.PRIMARY_KEY)) {
                    primaryKeys.add(columnValue);
                }
                if (column.getConstraint().contains(Constraint.UNIQUE)) {
                    uniqueKeys.add(columnValue);
                }
            }
        }
    }


    public Set<Object> getPrimaryKeys() {
        return primaryKeys;
    }

    public Map<Column, Set<Object>> getForeignKeys() {
        return foreignKeys;
    }

    public Set<Object> getUniqueKeys() {
        return uniqueKeys;
    }
}
