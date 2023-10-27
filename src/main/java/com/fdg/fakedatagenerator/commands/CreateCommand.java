package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidatorFactory;
import com.fdg.fakedatagenerator.validators.ConstraintType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
@Command(command = "create", group = "Create")
public class CreateCommand {
    @Autowired
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
    public void createTable(Scanner scanner) {
        System.out.print("Enter table name: ");
        String tableName = scanner.next();
        System.out.println("Available schemas: ");
        int i = 0;
        for (Schema schema : this.dataManager.getSchemas()) {
            System.out.print(i + ". ");
            schema.getColumns()
                  .forEach(column -> System.out.println(column.getName() + ", " + column.getDataType()
                                                                                        .getSimpleName()));
        }

        System.out.println("Enter schema index: ");
        int schemaIndex = scanner.nextInt();
        if (schemaIndex >= 0 && schemaIndex < this.dataManager.getSchemas().size()) {
            Schema schema = this.dataManager.getSchemas().get(schemaIndex);
            this.dataManager.addTable(tableName, schema);
        } else {
            System.out.println("Invalid index: " + schemaIndex);
        }

    }

    @Command(command = "schema", description = "Create a schema")
    private Schema createSchema(Scanner scanner, List<Column<?>> columns) {
        System.out.println("Available columns: ");
        int i = 0;
        for (Column<?> column : this.dataManager.getColumns()) {
            System.out.println(i + ". " + column.getName() + " " + column.getDataType().getSimpleName());
            i++;
        }

        System.out.println("Enter column indices separated by space: ");
        String[] inputs = scanner.nextLine().split(" ");

        List<Column<?>> selectedColumns = new ArrayList<>();

        for (String input : inputs) {
            try {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < columns.size()) {
                    selectedColumns.add(columns.get(index));
                } else {
                    System.out.println("Invalid index: " + index);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + input);
            }
        }

        log.info("Created schema with columns: " + selectedColumns);
        return new Schema(selectedColumns.toArray(new Column[0]));
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
