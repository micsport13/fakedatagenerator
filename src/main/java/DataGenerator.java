import Data.Column.Column;
import Data.Entities.Entity;
import Data.Schema.Schema;
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
        Schema schema = new Schema(
         new Column<>("id", Integer.class),
        new Column<>("first_name", String.class, new NotNullValidator()),
        new Column<>("last_name", String.class, new NotNullValidator()),
        new Column<>("email", String.class, new NotNullValidator()),
        new Column<>("is_admin", Boolean.class, new NotNullValidator())
        );
        Table members = new Table("Members",schema);
        members.addTableConstraint(schema.getColumn("id"), new PrimaryKeyValidator());
        members.addTableConstraint(schema.getColumn("id"), new UniqueValidator()); // FIXME: This overwrites existing validators.
        members.addTableConstraint(schema.getColumn("email"), new UniqueValidator());
        long startTime = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            Entity entity = new Entity.Builder(members.getSchema().getColumns().toArray(new Column[0]))
                    .withColumnValue("id", i)
                    .withColumnValue("first_name", faker.name()
                            .firstName())
                    .withColumnValue("last_name", faker.name()
                            .lastName())
                    .withColumnValue("email", faker.internet()
                            .emailAddress()+i)
                    .withColumnValue("is_admin",true)
                    .build();
            members.add(entity);
        }
        long endTime = System.nanoTime();
        //System.out.println(members);
        System.out.println(members.getSchema().toString());
        System.out.println("Time taken: " + -(startTime - endTime) / 1_000_000 + "ms");
        //System.out.println(members.printTable());
    }
}
