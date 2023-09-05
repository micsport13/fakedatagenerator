package Entities;

import Data.Column.Column;
import Data.TableConstraints.TableConstraint;

import java.util.*;

public class Table {
    private final String name;
    private final Map<Column, TableConstraint> tableConstraints = new HashMap<>();
    private final List<Entity> entities = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }
    public Table(String name, Entity entity) {
        this.name = name;
        for (Column column : entity.getColumns()) {
            this.tableConstraints.put(column, null);
        }
    }
    public void addTableConstraint(Column column, TableConstraint tableConstraint) {
        this.tableConstraints.put(column, Objects.requireNonNull(tableConstraint, "Table Constraint cannot be null"));
    }
    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public void add(Entity entity) {
        if (isValidEntity(entity)) {
            entities.add(entity);
        }
    }

    public String getName() {
        return name;
    }

    public Map<Column, TableConstraint> getTableConstraints() {
        return new HashMap<>(tableConstraints);
    }

    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column, TableConstraint> tableConstraints : this.tableConstraints.entrySet()) {
            if (tableConstraints.getValue() != null) {
                tableConstraints.getValue().isValid(entity.getColumnValueMapping().get(tableConstraints.getKey()));
            }
        }
        return true;
    }

    public void addForeignKeyValue(Table foreignTable, Column foreignColumn, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
