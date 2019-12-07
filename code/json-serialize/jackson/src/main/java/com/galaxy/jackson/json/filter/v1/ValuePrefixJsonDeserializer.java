package com.galaxy.jackson.json.filter.v1;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.galaxy.jackson.json.filter.ValuePrefix;

import java.io.IOException;

/**
 * @author galaxy
 */
public class ValuePrefixJsonDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {

    private String prefix = "";

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefix.class).prefix();
        return this;
    }


    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text != null) {
                return text.replace(prefix, "");
        }
        return text;
    }
}
