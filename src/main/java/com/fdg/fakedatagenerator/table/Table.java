package com.fdg.fakedatagenerator.table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.entities.Entity;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.serializers.table.TableDeserializer;
import com.fdg.fakedatagenerator.serializers.table.TableSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.*;

/**
 * The type Table.
 */
@JsonSerialize(using = TableSerializer.class)
@JsonDeserialize(using = TableDeserializer.class)
public class Table {

    @Getter
    private final @NotNull String name;

    @Getter
    private final Schema schema;
    private final List<Entity> entities = new ArrayList<>();

    public Table(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    /**
     * Add table constraint.
     *
     * @param column the column
     * @param tableConstraints the table constraints
     */
    public void addTableConstraint(Column<?> column, TableConstraint... tableConstraints) {
        var schemaColumns = this.schema.getTableConstraints();
        if (schemaColumns.containsKey(column)) {
            Set<TableConstraint> columnConstraints = schemaColumns.get(column);
            if (columnConstraints.containsAll(Arrays.asList(tableConstraints))) {
                System.out.println("Constraint already exists for column " + column.getName());
            } else {
                this.schema.getTableConstraints()
                           .get(column)
                           .addAll(Set.of(Objects.requireNonNull(tableConstraints, "Must provide a table constraint")));
            }
        }
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public List<Object> getColumnValues(String columnName) {
        List<Object> columnValues = new ArrayList<>();
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
        if (isValidEntity(entity)) { // TODO: Fix if schema doesn't match entity
            entities.add(entity);
        } else {
            throw new IllegalArgumentException("Entity is not valid for table " + this.name);
        }
    }

    public void addColumn(Column<?> column, TableConstraint... tableConstraints) {
        this.schema.addColumn(column, tableConstraints);
    }

    public void dropColumn(String columnName) {
        Optional<Column<?>> column = this.schema.getColumn(columnName);
        if (column.isPresent()) {
            this.schema.getTableConstraints().remove(column.get());
            this.entities.forEach(entity -> entity.getColumnValueMapping().getMap().remove(column.get()));
        } else {
            throw new IllegalArgumentException("Column with name " + columnName + " not found.");
        }
    }


    /**
     * Is valid entity boolean.
     *
     * @param entity the entity
     *
     * @return the boolean
     */
    public boolean isValidEntity(Entity entity) {
        for (Map.Entry<Column<?>, Set<TableConstraint>> tableConstraints : this.schema.getTableConstraints()
                                                                                      .entrySet()) {
            if (tableConstraints.getValue() != null) {
                tableConstraints.getValue()
                                .forEach(constraint -> constraint.validate(entity.getColumnValueMapping()
                                                                                 .get(tableConstraints.getKey())));
            }
        }
        return this.getSchema().getColumns().containsAll(entity.getColumnValueMapping().getMap().keySet());
    }

    @Override
    public String toString() {
        return "Table: " + name + "\n" + schema.toString();
    }

    public String printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ").append(name).append("\n");
        sb.append("Schema: \n").append(this.schema.toString());
        sb.append("Values: \n");
        for (Column<?> column : this.schema.getColumns()) {
            sb.append(column.getName()).append(",");
        }
        sb.append("\n");
        for (Entity entity : entities) {
            sb.append(entity.toRecord()).append("\n");
        }
        return sb.toString();
    }

}
