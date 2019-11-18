package com.galaxy.jackson.json.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.galaxy.jackson.bean.Panda;

import java.io.IOException;

/**
 * @author galaxy
 */
public class ValuePrefixJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(Panda.PREFIX + value);
    }
}
