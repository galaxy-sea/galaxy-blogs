package com.galaxy.jackson.json.filter.v2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.galaxy.jackson.json.filter.ValuePrefix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Galaxy
 */
public class ValuePrefixJsonDeserializerFactory extends JsonDeserializer<Object> implements ContextualDeserializer {

    private String prefix = "";

    private Collection collection;


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        prefix = beanProperty.getAnnotation(ValuePrefix.class).prefix();
        JavaType type = beanProperty.getType();

        // string类型
        if (type.isTypeOrSubTypeOf(String.class)) {
            return new ValuePrefixJsonDeserializer();
        }

        // Collection类型
        if (type.isCollectionLikeType()) {

            if (type.isTypeOrSubTypeOf(List.class)) {
                collection = new ArrayList();
            } else if (type.isTypeOrSubTypeOf(Set.class)) {
                collection = new HashSet();
            } else {
                throw new JsonMappingException("不是 list 或者 set 接口");
            }
            return new ValuePrefixCollectionJsonDeserializer();
        }

        // map类型
        //        if (property.getType().isMapLikeType()) {
        //            return this;
        //        }
        throw new JsonMappingException("不支持的类型,  仅支持 String, Collection, Map");
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return null;
    }


    /**
     * string ----------------------------------------
     */
    class ValuePrefixJsonDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String text = p.getText();
            if (text != null) {
                return text.replace(prefix, "");
            }
            return text;
        }
    }


    /**
     * Collection  ---------------------------------------
     */
    class ValuePrefixCollectionJsonDeserializer extends JsonDeserializer<Collection<String>> {


        @Override
        public Collection<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode jsonNode = jp.getCodec().readTree(jp);
            Iterator<JsonNode> elements = jsonNode.elements();
            return deserialize(elements);
        }

        private Collection deserialize(Iterator<JsonNode> elements) {
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
    }
}
