package com.galaxy.jackson.json.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.galaxy.jackson.bean.Panda;

import java.io.IOException;

/**
 * @author galaxy
 */
public class ValuePrefixJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text != null) {
            if (text.startsWith(Panda.PREFIX)) {
                return text.replace(Panda.PREFIX, "");
            }
        }
        return text;
    }
}
