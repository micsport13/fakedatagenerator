package Entities;

import Data.Column.Column;
import Data.TableConstraints.TableConstraint;

import java.util.*;

/**
 * The type Table.
 */
public class Table {
    private final String name;
    private final Map<Column, TableConstraint> tableConstraints = new HashMap<>();
    private final List<Entity> entities = new ArrayList<>();

    /**
     * Instantiates a new Table.
     *
     * @param name the name
     */
    public Table(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Table.
     *
     * @param name   the name
     * @param entity the entity
     */
    public Table(String name, Entity entity) {
        this.name = name;
        for (Column column : entity.getColumns()) {
            this.tableConstraints.put(column, null);
        }
    }

    /**
     * Add table constraint.
     *
     * @param column          the column
     * @param tableConstraint the table constraint
     */
    public void addTableConstraint(Column column, TableConstraint tableConstraint) {
        this.tableConstraints.put(column, Objects.requireNonNull(tableConstraint, "Table Constraint cannot be null"));
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    /**
     * Add.
     *
     * @param entity the entity
     */
    public void add(Entity entity) {
        if (isValidEntity(entity)) {
            entities.add(entity);
        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets table constraints.
     *
     * @return the table constraints
     */
    public Map<Column, TableConstraint> getTableConstraints() {
        return new HashMap<>(tableConstraints);
    }

    /**
     * Is valid entity boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column, TableConstraint> tableConstraints : this.tableConstraints.entrySet()) {
            if (tableConstraints.getValue() != null) {
                tableConstraints.getValue()
                        .isValid(entity.getColumnValueMapping()
                                         .get(tableConstraints.getKey()));
            }
        }
        return true;
    }

    /**
     * Add foreign key value.
     *
     * @param foreignTable  the foreign table
     * @param foreignColumn the foreign column
     * @param value         the value
     */
    public void addForeignKeyValue(Table foreignTable, Column foreignColumn, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ")
                .append(name)
                .append("\n");
        for (Entity entity : entities) {
            sb.append(entity.toRecord())
                    .append("\n");
        }
        return sb.toString();
    }

}
