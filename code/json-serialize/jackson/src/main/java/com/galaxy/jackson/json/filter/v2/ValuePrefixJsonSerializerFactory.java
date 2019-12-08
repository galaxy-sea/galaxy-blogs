package com.galaxy.jackson.json.filter.v2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.galaxy.jackson.json.filter.ValuePrefix;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Galaxy
 */
public class ValuePrefixJsonSerializerFactory extends JsonSerializer<Object> implements ContextualSerializer {

    private String prefix = "";

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefix.class).prefix();
        JavaType type = property.getType();

        // string类型
        if (type.isTypeOrSubTypeOf(String.class)) {
            return new StringPrefixJsonSerializer();
        }

        // Collection类型
        if (type.isCollectionLikeType()) {
            return new CollectionPrefixJsonSerializer();
        }

        // map类型
        //        if (property.getType().isMapLikeType()) {
        //            return this;
        //        }
        throw new JsonMappingException("不支持的类型,  仅支持 String, Collection, Map");
    }


    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }


    /**
     * --------string类型-------------------------------------------------
     */
   private class StringPrefixJsonSerializer extends JsonSerializer<String> {
        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                serializers.defaultSerializeNull(gen);
            } else {
                gen.writeString(prefix + value);
            }
        }
    }


    /**
     * --------Collection类型-------------------------------------------------
     */
    private class CollectionPrefixJsonSerializer extends JsonSerializer<Collection<String>> {

        @Override
        public void serialize(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
            g.setCurrentValue(value);
            int len = value.size();
            g.writeStartArray(len);
            serializeContents(value, g, provider);
            g.writeEndArray();
        }

        private final void serializeContents(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
            for (String str : value) {
                if (str == null) {
                    provider.defaultSerializeNull(g);
                } else {
                    g.writeString(prefix + str);
                }
            }
        }
    }
}
