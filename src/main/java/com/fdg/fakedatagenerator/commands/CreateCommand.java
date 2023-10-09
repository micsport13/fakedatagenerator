package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidatorFactory;
import com.fdg.fakedatagenerator.validators.ConstraintType;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.List;
import java.util.Scanner;

@Log4j2
@Command(command = "create", group = "Create")
public class CreateCommand {
    private final DataManager dataManager;

    public CreateCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    private static Class<?> getTypeFromClassname(String className) {
        return switch (className.toLowerCase()) {
            case "integer" -> Integer.class;
            case "string" -> String.class;
            case "boolean" -> Boolean.class;
            // Add other data types if needed
            default -> throw new IllegalArgumentException("Unknown data type class: " + className);
        };
    }

    @Command(command = "table", description = "Create a table")
    public void createTable(Scanner scanner, List<Column<?>> columns) {
        Schema schema = createSchema(columns);
        System.out.print("Enter table name: ");
        String tableName = scanner.next();
        this.dataManager.addTable(new Table(tableName, schema));
    }

    private Schema createSchema(List<Column<?>> columns) {
        log.info("Created schema with columns: " + columns);
        return new Schema(columns.toArray(new Column[0]));
    }

    @Command(command = "column", description = "Create a column")
    public void createColumn(String columnName, String dataType, @Option(required = false) String constraints) {
        // Assuming you have a method to convert String data type to Class type
        Class<?> columnType = getTypeFromClassname(dataType);
        if (constraints == null) {
            this.dataManager.addColumn(new Column<>(columnName, columnType));
            return;
        }
        switch (constraints.toUpperCase()) {
            case "NOT_NULL" -> {
                log.info("Created column with name: " + columnName + " and type: " + columnType + " and constraint: NotNull");
                this.dataManager.addColumn(new Column<>(columnName, columnType, ColumnValidatorFactory.createValidator(ConstraintType.NOT_NULL)));
            }
            case "CHECK" -> {
                ColumnValidator checkConstraint = ColumnValidatorFactory.createValidator("Test");
                log.info("Created column with name: " + columnName + " and type: " + columnType + " and constraint: " + checkConstraint);// TODO: Either accepted values or numbers
                this.dataManager.addColumn(new Column<>(columnName, columnType, checkConstraint));
            }
            default -> {
                throw new IllegalArgumentException("Constraint options: NOT_NULL, CHECK");
            }
        }
    }
}
