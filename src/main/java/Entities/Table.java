package Entities;

import Data.Column.Column;
import Data.TableConstraints.TableConstraint;
import Data.TableConstraints.UniqueConstraint;

import java.util.*;

public class Table<T extends Entity> {
    private final String name;
    private final Map<Column, Set<TableConstraint>> tableConstraints = new HashMap<>();
    //private final Map<Column, Set<Object>> uniqueColumns = new HashMap<>();  TODO: Validate on insertion
    private final List<T> entities = new ArrayList<>();

    public Table (String name) {
        this.name = name;
    }
    public void addTableConstraint(Column column, TableConstraint... tableConstraints) {
        this.tableConstraints.put(column, new HashSet<>());
        for (TableConstraint constraint : Objects.requireNonNull(tableConstraints, "Table constraints cannot be null")) {
            if (constraint instanceof UniqueConstraint) {
                this.uniqueColumns.put(column, new HashSet<>());
            } else {
                this.tableConstraints.get(column).add(constraint);
            }
        }
    }
    public List<T> getEntities() {
        return entities;
    }

    public void add(T entity) {
        if (isValidEntity(entity)) {
            entities.add(entity);
        }
        if (!this.uniqueColumns.keySet().isEmpty()) {
            for (Column column : this.uniqueColumns.keySet()) {
                this.uniqueColumns.get(column)
                        .add(entity.getColumnValueMapping()
                                     .get(column));
            }
        }
    }

    public String getName() {
        return name;
    }

    public Map<Column, Set<TableConstraint>> getTableConstraints() {
        return tableConstraints;
    }

    public boolean isValidEntity(T entity) {
        for (Column column : this.uniqueColumns.keySet()) {
            if (this.uniqueColumns.get(column).contains(entity.getColumnValueMapping().get(column))) {
                throw new IllegalArgumentException("Column " + column.getName() + " must be unique in table " + this.name);
            }
        }
        for (Column column : this.tableConstraints.keySet()) {
            for (TableConstraint tableConstraint : this.tableConstraints.get(column)) {
                if (!tableConstraint.isValid(entity.getColumnValueMapping().get(column))) {
                    throw new IllegalArgumentException("Column " + column.getName() + " does not meet the constraint " + tableConstraint);
                }
            }
        }
        return true;
    }

    public void addForeignKeyValue(Column column, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
