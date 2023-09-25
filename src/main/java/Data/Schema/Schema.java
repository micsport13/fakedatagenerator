package Data.Schema;

import Data.Column.Column;
import Data.Validators.TableValidators.TableValidator;
import Data.Validators.Validator;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Schema implements Validator {
    private final Map<Column<?>, Set<TableValidator>> tableConstraints = new LinkedHashMap<>();

    public Schema(Column<?>... columns){
        for (Column<?> column :columns) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column<?> column, TableValidator... tableValidators) {
        this.tableConstraints.put(column, new HashSet<>(Set.of(tableValidators)));
    }

    // TODO: Determine if this method should be removed
    public void addColumn(Column<?> column, Set<TableValidator> tableValidator) {
        if (this.tableConstraints.get(column) != null) {
            this.tableConstraints.get(column)
                    .addAll(tableValidator);
        } else {
            this.tableConstraints.put(column, new HashSet<>(tableValidator));  // TODO: Allow for other implementations?
        }
    }

    public Map<Column<?>, Set<TableValidator>> getTableConstraints() {
        return this.tableConstraints;
    }
    public Set<Column<?>> getColumns() {
        return this.tableConstraints.keySet();
    }

    @Override
    public void validate(Object value) {
        for (Map.Entry<Column<?>, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
            for (TableValidator tableValidator : entry.getValue()) {
                tableValidator.validate(value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Schema: \n");
        for (Map.Entry<Column<?>, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
            sb.append("\tName: ")
                    .append(entry.getKey()
                                    .getName())
                    .append("\n")
                    .append("\tData type: ")
                    .append(entry.getKey()
                                    .getDataType().getSimpleName())
                    .append("\n")
                    .append("\tConstraints: ")
                    .append(entry.getValue()
                                    .toString())
                    .append("\n").append("--------------------\n");
        }
        return sb.toString();
    }
}
