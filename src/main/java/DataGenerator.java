import Entities.Member;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker();
        List<Member> members = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 1; i <= 1_000_000; i++) {
            members.add(new Member.Builder().withColumnValue("Id", i)
                                .withColumnValue("FirstName", faker.name()
                                        .firstName())
                                .withColumnValue("Email", faker.internet()
                                        .emailAddress())
                                .withColumnValue("LastName", faker.name()
                                        .lastName())
                                .withColumnValue("Phone", faker.phoneNumber()
                                        .phoneNumber())
                                .build());
        }
        for (Member member : members) {
            System.out.println(member.toRecord());
        }
        long stop = System.nanoTime();
        System.out.println("Time elapsed: " + (stop - start) / 1_000_000 + " milliseconds");
    }
}
