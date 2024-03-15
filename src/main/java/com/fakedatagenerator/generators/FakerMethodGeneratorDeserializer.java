package com.fakedatagenerator.generators;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.datafaker.Faker;

public class FakerMethodGeneratorDeserializer extends JsonDeserializer<FakerMethodGenerator> {
  @Override
  public FakerMethodGenerator deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    Faker faker = (Faker) ctxt.findInjectableValue("faker", null, null);
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    String providerName = rootNode.get("provider").asText();
    String methodName = rootNode.get("method").asText();
    var arguments = rootNode.withArray("arguments").elements();
    List<Object> methodArguments = new ArrayList<>();
    for (Iterator<JsonNode> it = arguments; it.hasNext(); ) {
      var argument = it.next();
      methodArguments.add(readValue(argument, argument.get("type").asText()));
    }
    if (methodArguments.isEmpty()) {
      return new FakerMethodGenerator(faker, providerName, methodName);
    }
    return new FakerMethodGenerator(faker, providerName, methodName, methodArguments.toArray());
  }

  private Object readValue(JsonNode node, String valueType) {
    switch (valueType) {
      case "string" -> {
        return node.get("value").asText();
      }
      case "int" -> {
        return node.get("value").asInt();
      }
      case "double" -> {
        return node.get("value").asDouble();
      }
      case "long" -> {
        return node.get("value").asLong();
      }
      case "bool", "boolean" -> {
        node.get("value").asBoolean();
      }
    }
    throw new IllegalArgumentException("Unable to interpret argument type");
  }
}
