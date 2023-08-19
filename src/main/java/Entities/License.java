package Entities;

import Data.*;

import java.util.Map;
import java.util.Set;

public class License extends Entity{
    public License(Map<String, Object> values) {
        super(values);
    }

    @Override
    protected void setColumns() {
        this.columns.add(new Column(DataType.INT, "id", Set.of(Constraint.PRIMARY_KEY)));
        this.columns.add(new Column(DataType.INT, "int", Set.of(Constraint.FOREIGN_KEY)));
    }
}
