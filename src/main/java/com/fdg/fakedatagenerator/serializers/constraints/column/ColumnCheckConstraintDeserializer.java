package com.fdg.fakedatagenerator.serializers.constraints.column;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ColumnCheckConstraintDeserializer extends StdDeserializer<ColumnCheckConstraint> {

  public ColumnCheckConstraintDeserializer() {
    this(null);
  }

  protected ColumnCheckConstraintDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ColumnCheckConstraint deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode constraintNode = jsonParser.getCodec().readTree(jsonParser);
    if (constraintNode.at("/check_constraint/parameters") == null) {
      throw new IllegalArgumentException(
          "Check constraints must have parameters, either min/max or accepted values");
    }
    Iterator<Map.Entry<String, JsonNode>> fields =
        constraintNode.at("/check_constraint/parameters").fields();
    Number minValue = null;
    Number maxValue = null;
    List<String> acceptedValues = new ArrayList<>();
    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> node = fields.next();
      switch (node.getKey()) {
        case "min_value" -> minValue = node.getValue().numberValue();
        case "max_value" -> maxValue = node.getValue().numberValue();
        case "accepted_values" -> acceptedValues.addAll(
            fields.next().getValue().findValuesAsText("accepted_values"));
      }
    }
    if (minValue != null || maxValue != null) {
      return new ColumnCheckConstraint.Builder<>(new IntegerDataType())
          .withRange(minValue, maxValue)
          .build();
    }
    if (!acceptedValues.isEmpty()) {
      return new ColumnCheckConstraint.Builder<>(new VarcharDataType())
          .withAcceptedValues(acceptedValues)
          .build();
    }
    throw new IllegalArgumentException("Either min/max or accepted values must be set");
  }
}
