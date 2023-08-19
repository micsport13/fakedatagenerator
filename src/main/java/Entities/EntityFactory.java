package Entities;

import Data.Column;
import Data.Constraint;

import java.util.Map;

public interface EntityFactory {
    public Entity createEntity(String name, Map<String, Object> values);
    public boolean isValidEntity(Entity entity);

}
