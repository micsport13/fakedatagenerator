package Entities;

import Data.Column.Column;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Entity config.
 */
public class EntityConfig {
    private Set<Column> columns = new LinkedHashSet<>();

    /**
     * Load config entity config.
     *
     * @param configFile the config file
     * @return the entity config
     * @throws IOException the io exception
     */
    public EntityConfig loadConfig(String configFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(configFile), this.getClass());
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public Set<Column> getColumns() {
        return columns;
    }
}
