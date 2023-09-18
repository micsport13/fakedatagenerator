package Data.Table;

import Data.Column.Column;
import Data.Entities.Entity;
import Data.Schema.Schema;
import Data.Validators.TableValidators.TableValidator;

import java.util.*;

/**
 * The type Table.
 */
public class Table {
    private final String name;
    private final Schema schema;
    private final List<Entity> entities = new ArrayList<>();

    /**
     * Instantiates a new Table.
     *
     * @param name the name
     */
    public Table(String name) {
        this.name = name;
        this.schema = new Schema(new HashSet<>());
    }

    /**
     * Instantiates a new Table.
     *
     * @param name   the name
     * @param entity the entity
     */
    public Table(String name, Entity entity) {
        this.name = name;
        this.schema = new Schema(entity.getColumnValueMapping()
                                         .keySet());
    }

    public Table(String name, Set<Column> columnList) {
        this.name = name;
        this.schema = new Schema(columnList);
    }

    /**
     * Add table constraint.
     *
     * @param column          the column
     * @param tableConstraint the table constraint
     */
    public void addTableConstraint(Column column, TableValidator tableConstraint) {
        this.schema.addColumn(column, new HashSet<>(Set.of(Objects.requireNonNull(tableConstraint, "Table Validator cannot be null"))));
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public Set<Object> getColumnValues(String columnName) {
        Set<Object> columnValues = new HashSet<>();
        for (Entity entity : entities) {
            columnValues.add(entity.getValue(columnName));
        }
        return columnValues;
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
    public Map<Column, Set<TableValidator>> getSchema() {
        return this.schema.getTableConstraints();
    }


    /**
     * Is valid entity boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column, Set<TableValidator>> tableConstraints : this.schema.getTableConstraints()
                .entrySet()) {
            if (tableConstraints.getValue() != null) {
                tableConstraints.getValue()
                        .forEach(constraint -> constraint.validate(entity.getColumnValueMapping()
                                                                           .get(tableConstraints.getKey())));
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Table: " +
                name +
                "\n" + schema.toString();
    }

    public String printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ")
                .append(name)
                .append("\n");
        sb.append("Schema: \n")
                .append(this.schema.toString());
        for (Entity entity : entities) {
            sb.append(entity.toRecord())
                    .append("\n");
        }
        return sb.toString();
    }

}
