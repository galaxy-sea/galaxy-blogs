package com.galaxy.jackson.json.filter.v1;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author galaxy
 */
@JacksonStdImpl
public class ValuePrefixCollectionJsonDeserializer extends JsonDeserializer<Collection<String>> implements ContextualDeserializer {

    private String prefix = "";

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefixCollection.class).Prefix();
        return this;
    }


    @Override
    public Collection<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        Iterator<JsonNode> elements = jsonNode.elements();
        Field field = findField(jp.getCurrentName(), jp.getCurrentValue().getClass());
        Collection collection = getCollection(field);
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            if (node.isNull()) {
                collection.add(null);
            } else {
                String text = node.asText().replace(prefix, "");
                collection.add(text);
            }
        }
        return collection;
    }

    private Collection getCollection(Field field) {
        Class<?> type = field.getType();
        if (type.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        return new HashSet();
    }

    public Field findField(String name, Class<?> c) {
        for (; c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.getName().equals(name)) {
                    return field;
                }
            }
        }
        return null;
    }
}