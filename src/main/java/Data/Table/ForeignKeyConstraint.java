package Data.Table;

import Data.Column.Column;
import Entities.Entity;
import Entities.Table;

import java.util.*;

public class ForeignKeyConstraint implements TableConstraint{
    private final Map<Column, Set<Object>> foreignColumn;
    public ForeignKeyConstraint(Table<? extends Entity> foreignTable, String columnName) {
        Column column = foreignTable.getColumnByName(columnName);

        // Store the relationship between the columns
        foreignColumn = new HashMap<>();
        foreignColumn.put(column, foreignTable.getTableValues().get(column));
    }
    @Override
    public boolean isValid(Object value) {
        return this.foreignColumn.containsValue(value);
    }
}
