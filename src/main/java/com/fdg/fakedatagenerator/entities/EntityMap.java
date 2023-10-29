package com.fdg.fakedatagenerator.entities;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.DataType;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityMap {
    private final Map<Column<?>, Object> columnValueMapping = new LinkedHashMap<>();

    public <T> void add(Column<?> type, T value) {
        columnValueMapping.put(type, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Column<?> column) {
        Object value = columnValueMapping.get(column);
        if (value != null) {
            DataType<?> dataType = column.getDataType();
            return (T) dataType.deserialize(value.toString());
        }
        return null; // Handle the case where the value is null
    }

    public Map<Column<?>, Object> getMap() {
        return columnValueMapping;
    }
}
