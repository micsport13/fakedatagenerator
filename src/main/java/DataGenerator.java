import Entities.License;
import Entities.Member;
import Entities.Table;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;

public class DataGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker();
        Table<Member> members = new Table<>();
        Table<License> licenses = new Table<>();
        long start = System.nanoTime();
        for (int i = 1; i <= 1; i++) {
            members.add(new Member.Builder().withColumnValue("Id", i)
                                .withColumnValue("FirstName", faker.name()
                                        .firstName())
                                .withColumnValue("LastName", faker.name()
                                        .lastName())
                                .withColumnValue("Email", faker.internet()
                                        .emailAddress())
                                .withColumnValue("Phone", faker.phoneNumber().phoneNumber())
                                .withColumnValue("CreatedAt", ZonedDateTime.now(ZoneOffset.UTC))
                                .withColumnValue("UpdatedAt", ZonedDateTime.now(ZoneOffset.UTC))
                                .build());
        }
        for (Member member : members.getEntities()) {
            System.out.println(member.toRecord());
        }
        for (License license : licenses.getEntities()) {
            System.out.println(license.toRecord());
        }
        long stop = System.nanoTime();
        System.out.println("Time elapsed: " + (stop - start) / 1_000_000 + " milliseconds");
    }
}
