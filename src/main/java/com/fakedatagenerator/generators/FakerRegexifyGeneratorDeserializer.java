package com.fakedatagenerator.generators;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import net.datafaker.Faker;

public class FakerRegexifyGeneratorDeserializer extends JsonDeserializer<FakerRegexifyGenerator> {
  @Override
  public FakerRegexifyGenerator deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JacksonException {
      Faker faker = (Faker) ctxt.findInjectableValue("faker", null, null);
      JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
      String regexString = rootNode.get("regex").asText();
      return new FakerRegexifyGenerator(faker, regexString);
  }
}
