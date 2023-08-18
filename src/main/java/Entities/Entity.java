package Entities;
import java.Column;

public interface Entity {
    public Entity generate(List<Column<DataType, ?>, ?> columnValueMapping);
}
