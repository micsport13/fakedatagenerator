package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.serializers.ColumnConfig;
import com.fdg.fakedatagenerator.serializers.SchemaConfig;
import com.fdg.fakedatagenerator.validators.ColumnValidators.NotNullValidator;
import com.fdg.fakedatagenerator.validators.TableValidators.UniqueValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.command.annotation.Command;

import java.io.IOException;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
    private final DataManager dataManager;

    public ConfigCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Command(command = "load", description = "Load configuration from file")
    public static void loadConfig(String path) { //TODO: Load entire configuration, not just column
        try {
            Column<?> columnDeserialized = ColumnConfig.loadConfig("src/main/resources/test.yml");
            System.out.println(columnDeserialized);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Command(command = "save", description = "Write configuration to file")
    public static void writeConfig(String path) {
        try { // TODO: Write entire configuration, not just column/schema

            Column<?> column = new Column<>("Alma", String.class, new NotNullValidator());
            ColumnConfig.writeConfig("src/main/resources/testColumn.yml", column);
            Column<Integer> column2 = new Column<Integer>("id", Integer.class, new NotNullValidator());
            Schema testSchema = new Schema(column, column2);
            testSchema.addColumn(column, new UniqueValidator());
            SchemaConfig.writeConfig("src/main/resources/testSchema.yml", testSchema);
        } catch (IOException e) {
            log.error(e);
        }
    }
}

