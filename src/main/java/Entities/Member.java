package Entities;

import Data.Column;
import Data.Constraint;
import Data.DataType;

import java.util.Map;
import java.util.Set;

public final class Member extends Entity {

    public Member(Map<String, Object> values){
        super(values);
    }
    @Override
    protected void setColumns() {
        this.columns.add(new Column(DataType.INT, "id", Set.of(Constraint.PRIMARY_KEY)));
        this.columns.add(new Column(DataType.VARCHAR, "name", Set.of(Constraint.NOT_NULL)));
        this.columns.add(new Column(DataType.BOOLEAN, "isAdmin"));
    }

}
