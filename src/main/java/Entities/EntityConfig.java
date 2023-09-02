package Entities;

import Data.Column.Column;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
public class EntityConfig {
    private Set<Column> columns = new LinkedHashSet<>();

    public EntityConfig loadConfig(String configFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(configFile), this.getClass());
    }

    public Set<Column> getColumns() {
        return columns;
    }
}
