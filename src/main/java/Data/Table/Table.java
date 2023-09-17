package Data.Table;

import Data.Column.Column;
import Data.Entities.Entity;
import Data.Schema.TableSchema;
import Data.Validators.TableValidators.TableValidator;

import java.util.*;

/**
 * The type Table.
 */
public class Table {
    private final String name;
    private final Map<Column, TableValidator> tableConstraints = new HashMap<>();
    private TableSchema tableSchema = new TableSchema(new HashSet<>());
    private final List<Entity> entities = new ArrayList<>();

    /**
     * Instantiates a new Table.
     *
     * @param name the name
     */
    public Table(String name) {
        this.name = name;
    }
    public Table(String name, TableSchema tableSchema) {
        this.name = name;
        this.tableSchema = tableSchema;
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
    public Column getColumnByName(String columnName) {
        return this.tableSchema.getColumnByName(columnName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ")
                .append(name)
                .append("\n");
        sb.append(tableSchema.toString());
        return sb.toString();
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
