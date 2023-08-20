import Data.Column;
import Entities.*;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataGenerator {
    public static void main(String[] args) {
        EntityFactory<License> licenseFactory = new LicenseFactory();
        EntityFactory<Member> memberFactory = new MemberFactory();
        Faker faker = new Faker();
        License[] licenses = new License[10000];
        long start = System.nanoTime();
        for (int i=0; i<10;i++) {
            licenses[i] = licenseFactory.createEntity(new LinkedHashMap<>(Map.of("id", i, "int", faker.random().nextInt(100))));
        }
        for (License license : licenses) {
            System.out.println(license.toRecord());
        }

    }
}
