package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.serializers.ColumnConfig;
import com.fdg.fakedatagenerator.serializers.SchemaConfig;
import com.fdg.fakedatagenerator.validators.ColumnValidators.NotNullValidator;
import com.fdg.fakedatagenerator.validators.TableValidators.UniqueValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

import java.io.IOException;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
    @Autowired
    private final DataManager dataManager;

    public ConfigCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Command(command = "load", description = "Load configuration from file")
    public static void loadConfig(String path) { //TODO: Load entire configuration, not just column
        try {
            Column<?> columnDeserialized = ColumnConfig.loadConfig(path);
            System.out.println(columnDeserialized);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Command(command = "save", description = "Write configuration to file")
    public static void writeConfig(String path) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*
        try { // TODO: Write entire configuration, not just column/schema
        } catch (IOException e) {
            log.error(e);
        }*/
    }
    @Command(command = "print", description = "Prints out the current sessions configurations")
    public void printConfig() {
        System.out.println("Current session configurations:");
        this.dataManager.getTables().forEach(System.out::println);
        this.dataManager.getSchemas().forEach(System.out::println);
    }
}

