package Entities;

public class Member implements Entity {
    public final Integer id;
    public final String firstName;
    public final String lastName;
    public Member(Integer id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Entity generate(List<Column, ?> columnValueMapping) {
        return null;
    }
}
