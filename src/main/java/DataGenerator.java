import Entities.Entity;
import Entities.EntityFactory;
import Entities.Member;
import Entities.MemberFactory;

import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static void main(String[] args) {
        EntityFactory memberFactory = new MemberFactory();
        Entity member = memberFactory.createEntity("Member", Map.of("id", 1, "name", "John", "isAdmin", true));
    }
}
