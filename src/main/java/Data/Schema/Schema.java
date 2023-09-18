package Data.Schema;

import Data.Column.Column;
import Data.Validators.TableValidators.TableValidator;
import Data.Validators.Validator;

import java.util.*;

public class Schema implements Validator {
    private final Map<Column, Set<TableValidator>> tableConstraints = new LinkedHashMap<>();

    public Schema(Set<Column> columnSet) {
        for (Column column : new LinkedHashSet<>(columnSet)) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column column) {
        this.tableConstraints.put(column, new HashSet<>());
    }

    public void addColumn(Column column, Set<TableValidator> tableValidator) {
        if (this.tableConstraints.get(column) != null) {
            this.tableConstraints.get(column)
                    .addAll(tableValidator);
        } else {
            this.tableConstraints.put(column, tableValidator);
        }
    }

    public Map<Column, Set<TableValidator>> getTableConstraints() {
        return tableConstraints;
    }
    public Set<Column> getColumns() {
        return this.tableConstraints.keySet();
    }

    @Override
    public boolean validate(Object value) {
        for (Map.Entry<Column, Set<TableValidator>> entry : tableConstraints.entrySet()) {
            for (TableValidator tableValidator : entry.getValue()) {
                tableValidator.validate(value);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Schema: \n");
        for (Map.Entry<Column, Set<TableValidator>> entry : tableConstraints.entrySet()) {
            sb.append("\tName: ")
                    .append(entry.getKey()
                                    .getName())
                    .append("\n")
                    .append("\tData type: ")
                    .append(entry.getKey()
                                    .getDataType())
                    .append("\n")
                    .append("\tConstraints: ")
                    .append(entry.getValue()
                                    .toString())
                    .append("\n").append("--------------------\n");
        }
        return sb.toString();
    }
}
