package Data.Schema;

import Data.Column.Column;
import Data.Validators.TableValidators.TableValidator;
import Data.Validators.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Schema implements Validator {
    private final Map<Column, Set<TableValidator>> tableConstraints = new HashMap<>();

    public Schema(Set<Column> columnSet) {
        for (Column column : columnSet) {
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
            sb.append(entry.getKey()
                              .getName())
                    .append("\n\t Constraints: ")
                    .append(entry.getValue()
                                    .toString())
                    .append("\n");
        }
        return sb.toString();
    }
}
