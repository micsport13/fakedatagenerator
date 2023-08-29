package Entities;

import Data.Column.Column;
import Data.Table.TableConstraint;

import java.util.*;

public class Table<T extends Entity> {
    private String name;
    private final Map<Column, Set<Object>> tableValues = new LinkedHashMap<>();
    private final Map<Column, Set<TableConstraint>> tableConstraints = new HashMap<>();
    private final List<T> entities = new ArrayList<>();

    public Table (String name, Set<Column> columns) {
        this.name = name;
        for (Column column : columns) {
            this.tableValues.put(column, new HashSet<>());
            this.tableConstraints.put(column, new HashSet<>());
        }
    }
    public List<T> getEntities() {
        return entities;
    }

    public void add(T entity) {
        if (isValidEntity(entity)) {
            entities.add(entity);
        }
    }

    public String getName() {
        return name;
    }

    public Map<Column, Set<TableConstraint>> getTableConstraints() {
        return tableConstraints;
    }

    public boolean isValidEntity(T entity) {
        if (entity.getColumns().containsAll(this.tableValues.keySet())) {
            throw new IllegalArgumentException("Entity columns do not match table columns");
        }
        return true;
    }

    public void addForeignKeyValue(Column column, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<Column, Set<Object>> getTableValues() {
        return this.tableValues;
    }
    public Column getColumnByName(String name) {
        for (Column column : this.tableValues.keySet()) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        throw new IllegalArgumentException("Column " + name + " does not exist in table " + this.name);
    }

}
