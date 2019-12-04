package com.galaxy.jackson.json.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import com.galaxy.jackson.bean.Panda;

import java.io.IOException;
import java.util.Collection;

/**
 * @author galaxy
 */
@JacksonStdImpl
public class ValuePrefixCollectionJsonSerializer extends StaticListSerializerBase<Collection<String>> {

    private String prefix = "";

    public final static ValuePrefixCollectionJsonSerializer instance = new ValuePrefixCollectionJsonSerializer();

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

    protected ValuePrefixCollectionJsonSerializer() {
        super(Collection.class);
    }

    protected ValuePrefixCollectionJsonSerializer(ValuePrefixCollectionJsonSerializer src, Boolean unwrapSingle) {
        super(src, unwrapSingle);
    }

    @Override
    public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
        return new ValuePrefixCollectionJsonSerializer(this, unwrapSingle);
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