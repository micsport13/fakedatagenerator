import Data.Column.Column;
import Data.Entities.Entity;
import Data.Schema.Schema;
import Data.Table.Table;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.TableValidators.PrimaryKeyValidator;
import Data.Validators.TableValidators.UniqueValidator;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        System.out.println("4. Exit");
    }
    public static Column<?> createColumn(Scanner scanner) {
        System.out.print("Enter column name: ");
        String columnName = scanner.next();

        System.out.print("Enter data type (e.g., Integer, String, Boolean): ");
        String dataType = scanner.next();

        // Assuming you have a method to convert String data type to Class type
        Class<?> columnType = getTypeFromClassname(dataType);

        return new Column<>(columnName, columnType);
    }
    private static Schema createSchema(List<Column<?>> columns) {
        return new Schema(columns.toArray(new Column[0]));
    }
    private static void generateData(Table table, Faker faker, int numEntities) {
        for (int i; i<numEntities;i++) {
            Entity entity = new Entity.Builder(table.getSchema().getColumns().toArray(new Column[0]))
                    .withColumnValue("id", i)
                    .build();
            table.add(entity);
        }
        System.out.println(table.printTable());
    }
    private static Class<?> getTypeFromClassname(String className) {
        switch (className) {
            case "Integer":
                return Integer.class;
            case "String":
                return String.class;
            case "Boolean":
                return Boolean.class;
            // Add other data types if needed
            default:
                throw new IllegalArgumentException("Unknown data type class: " + className);
        }
    }
}
