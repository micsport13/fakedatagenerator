package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.table.Table;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class DataManager {

    private final Faker faker = new Faker();
    private static final Logger logger = LogManager.getLogger(DataManager.class);
    private final List<Table> tables = new ArrayList<>();


    public void generateData(Integer numEntities, @Option(required = false) String filePath) {
        for (Table table : this.tables) {
            // TODO: Figure out how to generate data for each table
            writeToOutput(table.printTable(),filePath);
        }
    }
    private void writeToOutput(String message, String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(new File(filePath), true));
                writer.println(message);
                writer.close();
            } catch (IOException e) {
                logger.error(e);
            }
        } else {
            System.out.println(message);
        }
    }

    public void addTable(Table table) {
    }

    public void addColumn(Column<?> column) {
    }
}
