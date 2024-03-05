package com.fakedatagenerator.generators;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;

@Log4j2
public class ValueGeneratorDeserializer extends StdDeserializer<ValueGenerator> {

  public ValueGeneratorDeserializer() {
    this(null);
  }

  protected ValueGeneratorDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ValueGenerator deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String provider = node.get("provider").asText();
    String method = node.get("method").asText();
    Faker faker = (Faker) deserializationContext.findInjectableValue((Object) "faker", null, null);
    if (node.get("arguments") == null) {
      return new FakerMethodGenerator(faker, provider, method);
    } else {
      Object[] methodArguments = new Object[node.get("arguments").size()];
      for (int i = 0; i < node.get("arguments").size(); i++) {
        try {
          methodArguments[i] = Integer.parseInt(node.get("arguments").get(i).asText());
        } catch (NumberFormatException e) {
          methodArguments[i] = node.get("arguments").get(i).asText();
        }
      }
      return new FakerMethodGenerator(faker, provider, method, methodArguments);
    }
  }

  private ValueGenerator deserializeExpressionGenerator(JsonNode node) {
    return null;
  }
  private ValueGenerator deserializeBothifyGenerator(JsonNode node) {
    return null;
  }
  private ValueGenerator deserializeCollectionGenerator(JsonNode node) {
    return null;
  }
  private ValueGenerator deserializeLetterifyGenerator(JsonNode node) {
    return null;
  }
  private ValueGenerator deserializeRegexifyGenerator(JsonNode node) {
    return null;
  }
  private ValueGenerator deserializeTemplatifyGenerator(JsonNode node) {
    return null;
  }
}
