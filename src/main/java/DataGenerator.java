import Data.Column.Column;
import Data.DataType.DataType;
import Data.Entities.Entity;
import Data.Table.Table;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.TableValidators.PrimaryKeyValidator;
import com.github.javafaker.Faker;

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
        Faker faker = new Faker();

        Column idColumn = new Column("id", DataType.INT);
        Column firstNameColumn = new Column("first_name", DataType.VARCHAR, new NotNullValidator());
        Column lastNameColumn = new Column("last_name", DataType.VARCHAR, new NotNullValidator());
        Column emailColumn = new Column("email", DataType.VARCHAR, new NotNullValidator());

        //table.addTableConstraint(emailColumn, new UniqueValidator());
            Table table = new Table("Members");
            table.addTableConstraint(idColumn, new PrimaryKeyValidator());
            long startTime = System.nanoTime();
            for (int i = 0; i < 250_000; i++) {
                Entity entity = new Entity.Builder(idColumn, firstNameColumn, lastNameColumn, emailColumn)
                        .withColumnValue("id", i)
                        .withColumnValue("first_name", faker.name()
                                .firstName())
                        .withColumnValue("last_name", faker.name()
                                .lastName())
                        .withColumnValue("email", faker.internet()
                                .emailAddress())
                        .build();
                table.add(entity);
            }
            long endTime = System.nanoTime();
        System.out.println("Time taken: " + -(startTime - endTime) / 1_000_000 + "ms");

        // TableSchema Testing
        /*Entity entity = new Entity.Builder(idColumn, firstNameColumn, lastNameColumn, emailColumn)
                .withColumnValue("id", 1)
                .withColumnValue("first_name", faker.name().firstName())
                .withColumnValue("last_name", faker.name().lastName())
                .withColumnValue("email", faker.internet().emailAddress()).build();
        TableSchema.SchemaBuilder schemaBuilder = new TableSchema.SchemaBuilder();
        schemaBuilder.addColumn("id", DataType.INT, new PrimaryKeyValidator());
        schemaBuilder.addColumn("first_name", DataType.VARCHAR, new NotNullValidator());
        schemaBuilder.addColumn("last_name", DataType.VARCHAR, new NotNullValidator());
        schemaBuilder.addColumn("email", DataType.VARCHAR, new UniqueValidator());
        TableSchema tableSchema = schemaBuilder.build();
        TableSchema tableSchema2 = new TableSchema(entity.getColumns());
        tableSchema2.addConstraint("email", new UniqueValidator());
        tableSchema2.addConstraint("email", new ColumnCheckValidator.CheckConstraintBuilder().withMinimumValue(1).build());
        Table table1 = new Table("Members", tableSchema);
        System.out.println(tableSchema);
        System.out.println(table1);
        System.out.println(tableSchema2);
        */

    }
}
