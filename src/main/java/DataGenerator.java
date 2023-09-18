import Data.Column.Column;
import Data.DataType.DataType;
import Data.Entities.Entity;
import Data.Table.Table;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.TableValidators.PrimaryKeyValidator;
import Data.Validators.TableValidators.UniqueValidator;
import com.github.javafaker.Faker;

import java.util.Set;

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
        Table members = new Table("Members", Set.of(idColumn, firstNameColumn, lastNameColumn, emailColumn));
        members.addTableConstraint(idColumn, new PrimaryKeyValidator());
        members.addTableConstraint(idColumn, new UniqueValidator());
        //members.addTableConstraint(emailColumn, new UniqueValidator());
        long startTime = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            Entity entity = new Entity.Builder(idColumn, firstNameColumn, lastNameColumn, emailColumn)
                    .withColumnValue("id", i)
                    .withColumnValue("first_name", faker.name()
                            .firstName() + i)
                    .withColumnValue("last_name", faker.name()
                            .lastName())
                    .withColumnValue("email", faker.internet()
                            .emailAddress())
                    .build();
            members.add(entity);
        }
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + -(startTime - endTime) / 1_000_000 + "ms");
        //System.out.println(members.printTable());
        System.out.println(members);
    }
}
