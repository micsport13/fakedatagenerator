import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Column<DataType, Constraint> {
    private final DataType dataType;
    private Set<Constraint> constraint;

    public Column(DataType dataType, Set<Constraint> constraints) {
        this.dataType = dataType;
        this.constraint = constraints;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Set<Constraint> getConstraint() {
        return constraint;
    }

    public void setConstraint(Set<Constraint> constraint) {
        this.constraint = constraint;
    }
}
