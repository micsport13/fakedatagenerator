import Data.Column.Column;
import Data.Column.NotNullConstraint;
import Data.DataType;
import Data.Exceptions.UniqueConstraintException;
import Data.TableConstraints.PrimaryKeyConstraint;
import Data.TableConstraints.UniqueConstraint;
import Entities.Entity;
import Entities.Table;
import com.github.javafaker.Faker;
import com.github.javafaker.Options;

import java.util.List;
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
        Table table = new Table("Members");
        Column idColumn = new Column("id", DataType.INT);
        Column firstNameColumn = new Column("first_name", DataType.VARCHAR, new NotNullConstraint());
        Column lastNameColumn = new Column("last_name", DataType.VARCHAR, new NotNullConstraint());
        Column emailColumn = new Column("email", DataType.VARCHAR, new NotNullConstraint());
        table.addTableConstraint(idColumn, new PrimaryKeyConstraint());
        table.addTableConstraint(emailColumn, new UniqueConstraint());
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
        System.out.println(table);
        System.out.println(table.getEntities().size());
        System.out.println("Time taken: " + (endTime - startTime)/1000000 + "ms");
    }
}
