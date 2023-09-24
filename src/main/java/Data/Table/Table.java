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
        this.schema = new Schema();
    }

    public Table(String name, Column... columns) {
        this.name = name;
        this.schema = new Schema(columns);
    }

    /**
     * Add table constraint.
     *
     * @param column          the column
     * @param tableConstraints the table constraints
     */
    public void addTableConstraint(Column column, TableValidator... tableConstraints) {
        // TODO: Prevalidate adding the constraint before adding the constraint
        this.schema.addColumn(column, Objects.requireNonNull(tableConstraints, "Table Validator cannot be null"));
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
        //TODO: Should this be a set or an array/list?
        //  Set only has context for eliminating duplicates
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
    public Schema getSchema() {
        return this.schema;
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
        sb.append("Values: \n");
        for (Column column : this.schema.getColumns()) {
            sb.append(column.getName())
                    .append(",");
        }
        sb.append("\n");
        for (Entity entity : entities) {
            sb.append(entity.toRecord())
                    .append("\n");
        }
        return sb.toString();
    }

}
