import Data.Column.Column;
import Data.Entities.Entity;
import Data.Table.Table;
import Data.Validators.ColumnValidators.NotNullValidator;
import Data.Validators.TableValidators.PrimaryKeyValidator;
import Data.Validators.TableValidators.UniqueValidator;
import com.github.javafaker.Faker;

import java.util.Random;

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

        Column<Integer> idColumn = new Column<>("id", Integer.class);
        Column<String> firstNameColumn = new Column<>("first_name", String.class, new NotNullValidator());
        Column<String> lastNameColumn = new Column<>("last_name", String.class, new NotNullValidator());
        Column<String> emailColumn = new Column<>("email", String.class, new NotNullValidator());
        Column<Boolean> isAdmin = new Column<>("is_admin", Boolean.class, new NotNullValidator());
        Table members = new Table("Members", idColumn, firstNameColumn, lastNameColumn, emailColumn, isAdmin);
        members.addTableConstraint(idColumn, new PrimaryKeyValidator());
        members.addTableConstraint(idColumn, new UniqueValidator()); // FIXME: This overwrites existing validators.
        //members.addTableConstraint(emailColumn, new UniqueValidator());
        long startTime = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            Entity entity = new Entity.Builder(members.getSchema().getColumns().toArray(new Column[0]))
                    .withColumnValue("id", i)
                    .withColumnValue("first_name", faker.name()
                            .firstName())
                    .withColumnValue("last_name", faker.name()
                            .lastName())
                    .withColumnValue("email", faker.internet()
                            .emailAddress())
                    .withColumnValue("is_admin","test")
                    .build();
            members.add(entity);
        }
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + -(startTime - endTime) / 1_000_000 + "ms");
        System.out.println(members);
        //System.out.println(members.getSchema().toString());
        System.out.println(members.printTable());
    }
}
