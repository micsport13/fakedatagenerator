package Data.Entities;

import Data.Column.Column;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityMap {
    private final Map<Column<?>, Object> columnValueMapping = new LinkedHashMap<>();

    public <T> void add(Column<T> type, T value) {
        columnValueMapping.put(type, value);
    }
    public <T> T get(Column<T> type) {
        return type.getDataType().cast(columnValueMapping.get(type));
    }
    public Map<Column<?>, Object> getMap() {
        return columnValueMapping;
    }
}
