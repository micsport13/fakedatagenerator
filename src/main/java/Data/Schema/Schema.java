package Data.Schema;

import Data.Column.Column;
import Data.Table.Table;
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
    public Schema(Column... columns){
        for (Column column :columns) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column column, TableValidator... tableValidators) {
        this.tableConstraints.put(column, new HashSet<>(Set.of(tableValidators)));
    }

    // TODO: Determine if this method should be removed
    public void addColumn(Column column, Set<TableValidator> tableValidator) {
        if (this.tableConstraints.get(column) != null) {
            this.tableConstraints.get(column)
                    .addAll(tableValidator);
        } else {
            this.tableConstraints.put(column, tableValidator);
        }
    }

    public Map<Column, Set<TableValidator>> getTableConstraints() {
        return this.tableConstraints;
    }
    public Set<Column> getColumns() {
        return this.tableConstraints.keySet();
    }

    @Override
    public boolean validate(Object value) {
        for (Map.Entry<Column, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
            for (TableValidator tableValidator : entry.getValue()) {
                tableValidator.validate(value);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Schema: \n");
        for (Map.Entry<Column, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
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
