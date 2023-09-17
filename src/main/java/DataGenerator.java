import Data.Column.Column;
import Data.DataType.DataType;
import Data.Entities.Entity;
import Data.Schema.TableSchema;
import Data.Table.Table;
import Data.Validators.ColumnValidators.ColumnCheckValidator;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.TableValidators.PrimaryKeyValidator;
import Data.Validators.TableValidators.UniqueValidator;
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
        Table table = new Table("Members");
        Column idColumn = new Column("id", DataType.INT);
        Column firstNameColumn = new Column("first_name", DataType.VARCHAR, new NotNullValidator());
        Column lastNameColumn = new Column("last_name", DataType.VARCHAR, new NotNullValidator());
        Column emailColumn = new Column("email", DataType.VARCHAR, new NotNullValidator());
        table.addTableConstraint(idColumn, new PrimaryKeyValidator());
        //table.addTableConstraint(emailColumn, new UniqueValidator());
        long startTime = System.nanoTime();
        for (int i=0; i<10000; i++){
            Entity entity = new Entity.Builder(idColumn, firstNameColumn, lastNameColumn, emailColumn)
                    .withColumnValue("id", i)
                    .withColumnValue("first_name", faker.name().firstName())
                    .withColumnValue("last_name", faker.name().lastName())
                    .withColumnValue("email", faker.internet().emailAddress()).build();
            table.add(entity);
        }
        long endTime = System.nanoTime();
        System.out.println(table.print());
        System.out.println(table.getEntities().size());
        System.out.println("Time taken: " + (endTime - startTime)/1000000 + "ms");

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
