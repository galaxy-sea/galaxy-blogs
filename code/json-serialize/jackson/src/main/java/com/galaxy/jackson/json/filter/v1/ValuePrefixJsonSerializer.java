package com.galaxy.jackson.json.filter.v1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.galaxy.jackson.json.filter.ValuePrefix;

import java.io.IOException;

/**
 * @author galaxy
 */
public class ValuePrefixJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private String prefix = "";



    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefix.class).prefix();
        return this;
    }


    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            serializers.defaultSerializeNull(gen);
        }else {
            gen.writeString(prefix + value);
        }
    }
}
