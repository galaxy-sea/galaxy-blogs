package com.galaxy.jackson.json.filter.v2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import com.galaxy.jackson.json.filter.ValuePrefix;
import com.galaxy.jackson.json.filter.v1.ValuePrefixCollection;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Galaxy
 */
public class ValuePrefixJsonSerializerFactory extends JsonSerializer<Object> implements ContextualSerializer {

    private String prefix = "";

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        prefix = property.getAnnotation(ValuePrefix.class).Prefix();
        JavaType type = property.getType();

        // string类型
        if (type.isTypeOrSubTypeOf(String.class)) {
            return new StringPrefixJsonSerializer();
        }

        // Collection类型
        if (property.getType().isCollectionLikeType()) {
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
    class StringPrefixJsonSerializer extends JsonSerializer<String> {
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
    class CollectionPrefixJsonSerializer extends StaticListSerializerBase<Collection<String>> {

        private String prefix = "";

        /*
        /**********************************************************
        /* Life-cycle
        /**********************************************************
         */

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
            prefix = property.getAnnotation(ValuePrefixCollection.class).Prefix();
            return super.createContextual(serializers, property);
        }

        protected CollectionPrefixJsonSerializer() {
            super(Collection.class);
        }

        protected CollectionPrefixJsonSerializer(CollectionPrefixJsonSerializer src, Boolean unwrapSingle) {
            super(src, unwrapSingle);
        }

        @Override
        public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
            return new CollectionPrefixJsonSerializer(this, unwrapSingle);
        }

        @Override
        protected JsonNode contentSchema() {
            return createSchemaNode("string", true);
        }

        @Override
        protected void acceptContentVisitor(JsonArrayFormatVisitor visitor) throws JsonMappingException {
            visitor.itemsFormat(JsonFormatTypes.STRING);
        }

        /*
        /**********************************************************
        /* Actual serialization
        /**********************************************************
         */

        @Override
        public void serialize(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
            g.setCurrentValue(value);
            final int len = value.size();
            if (len == 1) {
                if (((_unwrapSingle == null) && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || (_unwrapSingle.equals(Boolean.TRUE))) {
                    serializeContents(value, g, provider);
                    return;
                }
            }
            g.writeStartArray(len);
            serializeContents(value, g, provider);
            g.writeEndArray();
        }

        @Override
        public void serializeWithType(Collection<String> value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
            g.setCurrentValue(value);
            WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
            serializeContents(value, g, provider);
            typeSer.writeTypeSuffix(g, typeIdDef);
        }

        private final void serializeContents(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
            int i = 0;
            try {
                for (String str : value) {
                    if (str == null) {
                        provider.defaultSerializeNull(g);
                    } else {
                        g.writeString(prefix + str);
                    }
                    ++i;
                }
            } catch (Exception e) {
                wrapAndThrow(provider, e, value, i);
            }
        }
    }






}
