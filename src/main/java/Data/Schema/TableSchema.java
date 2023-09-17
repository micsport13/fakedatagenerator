package Data.Schema;

import Data.Column.Column;
import Data.DataType.DataType;
import Data.Validators.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TableSchema {
    private final Map<Column, Set<Validator>> schemaMap = new HashMap<>();

    public TableSchema(Set<Column> columnList) {
        for (Column column : columnList) {
            this.schemaMap.put(column, new HashSet<>());
        }
    }

    private TableSchema(SchemaBuilder schemaBuilder) {
        this.schemaMap.putAll(schemaBuilder.schemaMap);
    };
    public static class SchemaBuilder {
        private final Map<Column, Set<Validator>> schemaMap = new HashMap<>();

        public SchemaBuilder addColumn(String columnName, DataType dataType) {
            this.schemaMap.put(new Column(columnName, dataType), null);
            return this;
        }
        public SchemaBuilder addColumn(String columnName, DataType datatype, Validator... validators) {
            this.schemaMap.put(new Column(columnName, datatype), Set.of(validators));
            return this;
        }

        public TableSchema build() {
            return new TableSchema(this);
        }
    }
    public void addConstraint(String columnName, Validator validator) {
        Column column = this.getColumnByName(columnName);
        this.schemaMap.get(column).add(validator);
    }
    public Column getColumnByName(String columnName) {
        for (Column column : this.schemaMap.keySet()) {
            if (column.getName().equals(columnName)) {
                return column;
            }
        }
        throw new IllegalArgumentException(columnName + " not found in the schema");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TableSchema: \n");
        for (Map.Entry<Column, Set<Validator>> entry : this.schemaMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                stringBuilder.append(entry.getKey().toString())
                        .append("\n====================\n");
            } else {
                stringBuilder.append(entry.getKey()
                                             .toString())
                        .append("\n")
                        .append("Table Constraints: ")
                        .append(entry.getValue().toString())
                        .append("\n====================\n");
            }
        }
        return stringBuilder.toString();
    }
}
