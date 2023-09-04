package Entities;

import Data.TableConstraints.UniqueConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableTest {
    private Table<Member> table;

    @BeforeEach
    public void setUp() {
        this.table = new Table<>("TableTest");
    }
    @Test
    public void addMemberThrowsNoException() {
        Member member = new Member.Builder().build();
        this.table.add(member);
    }
    @Test
    public void addMultipleMembersThrowsNoException() {
        Member member = new Member.Builder().build();
        Member member2 = new Member.Builder().build();
        this.table.add(member);
        this.table.add(member2);
    }
    @Test
    public void addMemberWithUniqueColumnThrowsNoException() {
        Member member = new Member.Builder().withColumnValue("name", "John").build();
        this.table.addTableConstraint(member.getColumnByName("name"), new UniqueConstraint());
        this.table.add(member);
    }
    @Test
    public void addMultipleMembersWithUniqueValuesThrowsNoException() {
        Member member = new Member.Builder().withColumnValue("name", "John").build();
        Member member2 = new Member.Builder().withColumnValue("name", "Dave").build();
        this.table.addTableConstraint(member.getColumnByName("name"), new UniqueConstraint());
        this.table.add(member);
        Assertions.assertDoesNotThrow(() -> this.table.add(member2));
    }
    @Test
    public void addMultipleMembersWithNonUniqueValuesThrowsException() {
        Member member = new Member.Builder().withColumnValue("name", "John").build();
        this.table.addTableConstraint(member.getColumnByName("name"), new UniqueConstraint());
        this.table.add(member);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.table.add(member));
    }

}