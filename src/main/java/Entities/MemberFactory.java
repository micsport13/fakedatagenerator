package Entities;
import Data.Column;
import Data.Constraint;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemberFactory implements EntityFactory {
    private final Set<?> primaryKeys = new HashSet<>();
    private final Set<?> foreignKeys = new HashSet<>();
    private final Set<?> uniqueKeys = new HashSet<>();
    public Entity createEntity(String entityName, Map<String, Object> values) {
        switch (entityName.toUpperCase()) {
            case "MEMBER":
                Member member = new Member(values);
                if (this.isValidEntity(member)) {
                    return member;
                }
            case "LICENSE":
                return new License(values);
            default:
                throw new IllegalArgumentException("Invalid entity name");
        }
    }
    @Override
    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column, Object> entry : entity.getColumnValueMapping().entrySet()){
            if (entry.getKey().getConstraint().contains(Constraint.PRIMARY_KEY) && this.primaryKeys.contains(entry.getValue())){
                throw new RuntimeException("Contains a value that violates primary key constraint");
            }
            if (entry.getKey().getConstraint().contains(Constraint.UNIQUE) && this.uniqueKeys.contains(entry.getValue())) {
                throw new RuntimeException("Contains a value that violates unique key constraint");
            }
            if (entry.getKey().getConstraint().contains(Constraint.FOREIGN_KEY) && !this.foreignKeys.contains(entry.getValue())) {
                throw new RuntimeException("Contains a value that violates foreign key constraint");
            }
        }
        return true;
    }
}
