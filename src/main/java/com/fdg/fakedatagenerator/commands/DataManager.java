package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Log4j2
public class DataManager {

    private final Faker faker = new Faker();

    @Getter
    private final List<Table> tables = new ArrayList<>();
    @Getter
    private final List<Column<?>> columns = new ArrayList<>();
    @Getter
    private final List<Schema> schemas = new ArrayList<>();


    public void generateData(Integer numEntities, @Option(required = false) String filePath) {
        for (Table table : this.tables) {
            // TODO: Figure out how to generate data for each table
            writeToOutput(table.printTable(), filePath);
        }
    }

    private void writeToOutput(String message, String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            try {
                log.info("Writing to file: " + filePath);
                PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
                writer.println(message);
                writer.close();
            } catch (IOException e) {
                log.error(e);
            }
        } else {
            System.out.println(message);
        }
    }

    public void addSchema(Column<?>... columns) {
        Schema schema = new Schema(columns);
        this.schemas.add(schema);
    }

    public void addTable(String tableName, Schema schema) {
        Table table = new Table(tableName, schema);
        this.tables.add(table);
    }

    public void addColumn(Column<?> column) {
        this.columns.add(column);
    }
}
