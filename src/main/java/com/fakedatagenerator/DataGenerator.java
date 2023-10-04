package com.fakedatagenerator;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.column.ColumnConfig;
import com.fakedatagenerator.entities.Entity;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fakedatagenerator.validators.ColumnValidators.ColumnValidatorFactory;
import com.fakedatagenerator.validators.ColumnValidators.NotNullValidator;
import com.fakedatagenerator.validators.ConstraintType;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Data generator.
 */
public class DataGenerator {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Faker faker = new Faker();
        List<Column<?>> columns = new ArrayList<>();
        Table table = null;
        boolean loop = true;
        while (loop) {
            printOptions();

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    columns.add(createColumn(scanner));
                    break;
                case 2:
                    Schema schema = createSchema(columns);
                    System.out.print("Enter table name: ");
                    String tableName = scanner.next();
                    table = new Table(tableName, schema);
                    break;
                case 3:
                    System.out.print("Enter number of entities to generate: ");
                    int numEntities = scanner.nextInt();
                    generateData(table, faker, numEntities);
                    break;
                case 4:
                    try {
                        Column<?> column = new Column<>("Alma", String.class, new NotNullValidator());
                        ColumnConfig.writeConfig("src/main/resources/test.yml", column);
                        Column<?> columnDeserialized = ColumnConfig.loadConfig("src/main/resources/test.yml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 5:
                    loop = false;
            }

        }
        scanner.close();
        //System.out.println(members.printTable());
    }

    public static void printOptions() {
        System.out.println("1. Create column");
        System.out.println("2. Create table");
        System.out.println("3. Generate data");
        System.out.println("4. Load Config");
        System.out.println("5. Exit");
    }
    public static Column<?> createColumn(Scanner scanner) {
        System.out.print("Enter column name: ");
        String columnName = scanner.next();

        System.out.print("Enter data type (e.g., Integer, String, Boolean): ");
        String dataType = scanner.next();

        System.out.print("Enter constraint (Either NOT_NULL or CHECK): ");
        String constraints = scanner.next();
        // Assuming you have a method to convert String data type to Class type
        Class<?> columnType = getTypeFromClassname(dataType);
        switch (constraints) {
            case "NOT_NULL" -> {
                return new Column<>(columnName, columnType, ColumnValidatorFactory.createValidator(ConstraintType.NOT_NULL));
            }
            case "CHECK" -> {
                ColumnValidator checkConstraint = ColumnValidatorFactory.createValidator("Test"); // TODO: Either accepted values or numbers
                return new Column<>(columnName, columnType, checkConstraint);
            }
        default -> {return new Column<>(columnName, columnType);}
        }
    }
    private static Schema createSchema(List<Column<?>> columns) {
        return new Schema(columns.toArray(new Column[0]));
    }
    private static void generateData(Table table, Faker faker, Integer numEntities) {
        for (int i = 0; i < numEntities; i++) {
            Entity entity = new Entity.Builder(table.getSchema().getColumns().toArray(new Column[0])) // TODO: Make this generic
                    .withColumnValue(table.getSchema().getColumns().toArray(new Column[0])[0].getName(),i)
                    .build();
            table.add(entity);
        }
        System.out.println(table.printTable());
    }
    private static Class<?> getTypeFromClassname(String className) {
        return switch (className) {
            case "Integer" -> Integer.class;
            case "String" -> String.class;
            case "Boolean" -> Boolean.class;
            // Add other data types if needed
            default -> throw new IllegalArgumentException("Unknown data type class: " + className);
        };
    }
}
