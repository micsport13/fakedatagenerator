package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.factories.DataTypeFactory;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.constraints.ColumnLevelConstraints;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraintFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.*;

@Log4j2
@Command(command = "create", group = "Create")
public class CreateCommand {
    @Autowired
    private final DataManager dataManager;

    public CreateCommand(DataManager dataManager) {
        this.dataManager = dataManager;
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
                  .forEach(column -> System.out.println(column.getName() + ", " + column.getDataType().toString()));
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
    private Schema createSchema(Scanner scanner) {
        System.out.println("Available columns: ");
        int i = 0;
        List<Column<?>> columns = this.dataManager.getColumns();
        for (Column<?> column : this.dataManager.getColumns()) {
            System.out.println(i + ". " + column.getName() + " " + column.getDataType().toString());
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
    public void createColumn(String name, String dataType,
                             @Option(required = false) Boolean hasParameters,
                             @Option(required = false) String constraints) {
        // Assuming you have a method to convert String data type to Class type
        Map<String, Object> parameters = new HashMap<>();
        DataType<?> columnType = DataTypeFactory.create(dataType, parameters);
        if (constraints == null) {
            this.dataManager.addColumn(new Column<>(name, columnType));
            return;
        }
        switch (constraints.toUpperCase()) {
            case "NOT_NULL", "NOTNULL" -> {
                log.info("Created column with name: " + name + " and type: " + columnType + " and constraint: NotNull");
                this.dataManager.addColumn(new Column<>(name, columnType, ColumnConstraintFactory.createValidator(ColumnLevelConstraints.NOT_NULL)));
            }
            case "CHECK" -> {
                ColumnConstraint checkConstraint = ColumnConstraintFactory.createValidator("Test"); // TODO: Read in potential check constraint values
                log.info("Created column with name: " + name + " and type: " + columnType + " and constraint: " + checkConstraint);// TODO: Either accepted values or numbers
                this.dataManager.addColumn(new Column<>(name, columnType, checkConstraint));
            }
            default -> {
                throw new IllegalArgumentException("Constraint options: NOT_NULL, CHECK");
            }
        }
    }
}
