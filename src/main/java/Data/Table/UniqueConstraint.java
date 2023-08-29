package Data.Table;

import Data.Column.Column;
import Entities.Entity;
import Entities.Table;

import java.util.HashMap;
import java.util.Map;

public class UniqueConstraint implements TableConstraint {
    private final Map<Column, Object> uniqueColumn;

    public UniqueConstraint(Table<? extends Entity> hostTable, String columnName) {
        Column column = hostTable.getColumnByName(columnName);

        // Store the relationship between the columns
        this.uniqueColumn = new HashMap<>();
        this.uniqueColumn.put(column, hostTable.getTableValues().get(column));
    }
    @Override
    public boolean isValid(Object value) {
        return !this.uniqueColumn.containsValue(value);
    }
}
