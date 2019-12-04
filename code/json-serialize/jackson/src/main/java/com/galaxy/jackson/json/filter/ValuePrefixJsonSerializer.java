package com.galaxy.jackson.json.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * @author galaxy
 */
public class ValuePrefixJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private String prefix = "";

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefix.class).Prefix();
        if (property.getType().isCollectionLikeType()) {
            return new ValuePrefixJsonSerializer();
        }
        return this;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(prefix + value);
    }
}
