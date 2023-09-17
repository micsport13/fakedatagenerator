package Data.Table;

import Data.Column.Column;
import Data.Entities.Entity;
import Data.Validators.TableValidators.TableValidator;

import java.util.*;

/**
 * The type Table.
 */
public class Table {
    private final String name;
    private final Map<Column, TableValidator> tableConstraints = new HashMap<>();
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
    public void addTableConstraint(Column column, TableValidator tableConstraint) {
        this.tableConstraints.put(column, Objects.requireNonNull(tableConstraint, "Table Validator cannot be null"));
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
    public Map<Column, TableValidator> getTableConstraints() {
        return new HashMap<>(tableConstraints);
    }


    /**
     * Is valid entity boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column, TableValidator> tableConstraints : this.tableConstraints.entrySet()) {
            if (tableConstraints.getValue() != null) {
                tableConstraints.getValue()
                        .validate(entity.getColumnValueMapping()
                                         .get(tableConstraints.getKey()));
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Table: " +
                name +
                "\n";
    }
    public String print() {
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
