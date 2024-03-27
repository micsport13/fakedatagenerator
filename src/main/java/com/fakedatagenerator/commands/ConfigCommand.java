package com.fakedatagenerator.commands;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.factories.ConstraintFactory;
import com.fakedatagenerator.constraints.factories.ConstraintFactoryProvider;
import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.factories.DataTypeFactory;
import com.fakedatagenerator.datatypes.factories.DataTypeFactoryProvider;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.generators.factories.ValueGeneratorFactory;
import com.fakedatagenerator.generators.factories.ValueGeneratorFactoryProvider;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.utils.FactoryOptions;
import com.fakedatagenerator.utils.Primitives;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
  private final EntityConfig entityConfig;

  @Autowired
  public ConfigCommand(EntityConfig entityConfig) {
    this.entityConfig = entityConfig;
  }

  @Command(command = "load", description = "Load configuration from file")
  public void loadConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath =
        Path.of(
            System.getProperty("user.dir"),
            path); // TODO: Figure out how to set path better than this
    try {
      entityConfig.loadConfig(filePath.toString());
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "save", description = "Write configuration to file")
  public void writeConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      entityConfig.writeConfig(path);
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "print", description = "Prints out the current sessions configurations")
  public void printConfig() {
    System.out.println("Current session configurations:");
    this.entityConfig
        .getDataManager()
        .getTables()
        .forEach((key, value) -> System.out.println(value));
  }

  @Command(command = "build", description = "Build a configuration file", hidden = true)
  public void buildConfig() {
    String options = "";
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Options: \n1. (C) Create Table\n2. (Q) Quit");
      options = scanner.nextLine();
      if (options.equalsIgnoreCase("q")) {
        break;
      }
      if (options.equalsIgnoreCase("c")) {
        Table table = buildTable();
        this.entityConfig.getDataManager().addTable(table);
      }
    }
  }

  private Table buildTable() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter a table name:");
    String tableName = scanner.nextLine();
    Schema schema = new Schema();
    while (true) {
      System.out.println(
          """
      Options (You must create columns before you can create constraints):
      1. Create Column
      2. Create Constraint
      3. Quit""");
      int option = scanner.nextInt();
      switch (option) {
        case 1 -> {
          System.out.println("Please enter a column name");
          String columnName = scanner.nextLine();
          System.out.println("Enter a data type");
          String dataTypeString = scanner.nextLine();
          DataType<?> dataType = readDataType(dataTypeString);
          System.out.println("Enter a value generator type");
          String valueGeneratorTypeString = scanner.nextLine();
          ValueGenerator valueGenerator = readValueGenerator(valueGeneratorTypeString);
          Column column = new Column(columnName, dataType, valueGenerator);
          schema.addColumn(column);
        }
        case 2 -> {
          System.out.println("Please enter a constraint type");
          String constraintType = scanner.nextLine();
          Constraint constraint = readConstraint(constraintType);
          List<Column> constraintColumns = new ArrayList<>();
          System.out.println(
              "Please enter the columns that apply to this constraint.  Press 'q' to quit.");
          while (!scanner.nextLine().equals("q")) {
            constraintColumns.add(schema.getColumn(scanner.nextLine()));
          }
          schema.addConstraint(constraint, constraintColumns.toArray(new Column[0]));
        }
        case 3 -> {
          System.out.println("Finished building table");
          return new Table(tableName, schema);
        }
        default -> {
          System.out.println("Invalid option, please enter one of the options");
          continue;
        }
      }
    }
  }

  private DataType<?> readDataType(String dataTypeString) {
    DataTypeFactory dataTypeFactory = DataTypeFactoryProvider.getDataTypeFactory(dataTypeString);
    Map<String, Object> args = readArgs(dataTypeFactory);
    return dataTypeFactory.createDataType(dataTypeString, args);
  }

  private ValueGenerator readValueGenerator(String valueGeneratorTypeString) {
    ValueGeneratorFactoryProvider valueGeneratorFactoryProvider =
        new ValueGeneratorFactoryProvider(this.entityConfig.faker);
    ValueGeneratorFactory valueGeneratorFactory =
        valueGeneratorFactoryProvider.getValueGeneratorFactory(valueGeneratorTypeString);
    Map<String, Object> valueGeneratorArgs = readArgs(valueGeneratorFactory);
    return valueGeneratorFactory.createValueGenerator(valueGeneratorTypeString, valueGeneratorArgs);
  }

  private Constraint readConstraint(String constraintType) {
    ConstraintFactoryProvider constraintFactoryProvider =
        new ConstraintFactoryProvider(this.entityConfig.getDataManager());

    ConstraintFactory constraintFactory =
        constraintFactoryProvider.getConstraintFactory(constraintType);
    Map<String, Object> args = readArgs(constraintFactory);
    return constraintFactory.createConstraint(constraintType, args);
  }

  private Map<String, Object> readArgs(FactoryOptions factory) {
    Map<String, Primitives> constraintArgs = factory.getOptions();
    Map<String, Object> args = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    for (var option : constraintArgs.entrySet()) {
      System.out.println("Option: " + option.getKey());
      System.out.print("Value: ");
      Object value;
      switch (option.getValue()) {
        case INT -> value = scanner.nextInt();
        case STRING -> value = scanner.nextLine();
        case FLOAT -> value = scanner.nextFloat();
        case DOUBLE -> value = scanner.nextDouble();
        case LONG -> value = scanner.nextLong();
        case BYTE -> value = scanner.nextByte();
        case CHAR -> value = scanner.next().charAt(0);
        case SHORT -> value = scanner.nextShort();
        case BOOLEAN -> value = scanner.nextBoolean();
        case STRING_ARRAY -> {
          List<String> stringArray = new ArrayList<>();
          while (!scanner.nextLine().equals("q")) {
            System.out.println("Enter a string (q to quit):");
            stringArray.add(scanner.nextLine());
          }
          value = stringArray.toArray();
        }
        case INT_ARRAY -> {
          List<Integer> intArray = new ArrayList<>();
          while (!scanner.nextLine().equals("q")) {
            System.out.println("Enter an integer (q to quit):");
            intArray.add(scanner.nextInt());
          }
          value = intArray.toArray();
        }
        case OBJECT_ARRAY -> { // TODO: Figure out how to pass array of objects
          List<Object> objectArray = new ArrayList<>();
          while (!scanner.nextLine().equals("q")) {
            System.out.printf(
                "Enter primitive object type: (%s)%n", String.join(", ", Primitives.getAliases()));
            Primitives primitive = Primitives.fromString(scanner.nextLine());
            System.out.println("Enter an object (q to quit):");
            objectArray.add(readValue(primitive));
          }
          value = objectArray.toArray();
        }
        default -> {
          System.out.println("Invalid option, please enter one of the options");
          continue;
        }
      }
      args.put(option.getKey(), value);
    }
    return args;
  }

  private Object readValue(Primitives primitive) {
    Scanner scanner = new Scanner(System.in);
    return switch (primitive) {
      case INT -> scanner.nextInt();
      case STRING -> scanner.nextLine();
      case FLOAT -> scanner.nextFloat();
      case DOUBLE -> scanner.nextDouble();
      case LONG -> scanner.nextLong();
      case BYTE -> scanner.nextByte();
      case CHAR -> scanner.next().charAt(0);
      case SHORT -> scanner.nextShort();
      case BOOLEAN -> scanner.nextBoolean();
      case INT_ARRAY, STRING_ARRAY, OBJECT_ARRAY -> {
        throw new IllegalArgumentException("Cannot read array types");
      }
    };
  }
}
