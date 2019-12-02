package com.galaxy.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.galaxy.jackson.bean.Panda;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//@SpringBootTest
class JacksonApplicationTests {


    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void contextLoads() {
    }


    @Test
    public void JsonSerializer() throws JsonProcessingException {
        Panda panda = new Panda("132123", null, 17);

        List<String> data = new ArrayList<String>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        panda.setNameList(data);

        String json = objectMapper.writeValueAsString(panda);
        System.out.println(json);
    }

    @Test
    public void JsonDeserializer() throws JsonProcessingException {
        Panda panda = objectMapper.readValue("{\"phone\":\"前缀132123\",\"name\":null,\"age\":17,\"nameList\":[\"前缀1\",\"前缀2\",\"前缀3\",\"前缀4\"]}", Panda.class);

        objectMapper.registerModule(new SimpleModule());


        objectMapper.registerModule(new Module() {
            @Override
            public String getModuleName() {
                return null;
            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public void setupModule(SetupContext context) {

            }
        });


        System.out.println(panda);
    }


    /**
     * Assume you have a custom collection.
     */
    @JsonDeserialize(as = ImmutableBag.class)
    public interface Bag<T> extends Collection<T> {

    }

    /**
     * It is deserialized as an immutable version (i.e. no add / remove, etc.).
     */
    public static class ImmutableBag<T> extends AbstractCollection<T> implements Bag<T> {
        @Override
        public Iterator<T> iterator() {
            return elements.iterator();
        }

        @Override
        public int size() {
            return elements.size();
        }

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        private ImmutableBag(Collection<T> elements) {
            this.elements = Collections.unmodifiableCollection(elements);
        }

        private final Collection<T> elements;
    }

    public static class Value {
        public Value(String value) {
            this.value = value;
        }

        private String value;
    }

    /**
     * This does not work - see {@link #testDeserializeBagOfStrings}.
     */
    public static class WithBagOfStrings {

        public Bag<String> getStrings() {
            return this.bagOfStrings;
        }

        public void setStrings(Bag<String> bagOfStrings) {
            this.bagOfStrings = bagOfStrings;
        }

        private Bag<String> bagOfStrings;
    }

    /**
     * This DOES work - see {@link #testDeserializeBagOfObjects}.
     */
    public static class WithBagOfValues {
        public Bag<Value> getValues() {
            return this.bagOfValues;
        }

        public void setValues(Bag<Value> bagOfValues) {
            this.bagOfValues = bagOfValues;
        }

        private Bag<Value> bagOfValues;
    }


    @Test
    public void testDeserializeBagOfStrings() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WithBagOfStrings result = mapper.readerFor(WithBagOfStrings.class)
                .readValue("{\"strings\": [ \"a\", \"b\", \"c\"]}");
        // Fails with:
        // com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of
        // `...$ImmutableBag` (although at least one Creator exists): no default no-arguments constructor found
        // at [Source: (String)"{"strings": [ "a", "b", "c"]}"; line: 1, column: 13] (through reference chain:
        // ...$WithBagOfStrings["strings"])
        //
        //	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:63)
        //	at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1343)
        //	at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1032)
        //	at com.fasterxml.jackson.databind.deser.ValueInstantiator.createUsingDefault(ValueInstantiator.java:189)
        //	at com.fasterxml.jackson.databind.deser.std.StdValueInstantiator.createUsingDefault(StdValueInstantiator.java:267)
        //	at com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer.deserialize(StringCollectionDeserializer.java:168)
        //	at com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer.deserialize(StringCollectionDeserializer.java:21)
        //	at com.fasterxml.jackson.databind.deser.impl.MethodProperty.deserializeAndSet(MethodProperty.java:127)
        //	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:288)
        //	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:151)
        //	at com.fasterxml.jackson.databind.ObjectReader._bindAndClose(ObjectReader.java:1611)
        //	at com.fasterxml.jackson.databind.ObjectReader.readValue(ObjectReader.java:1219)
        //        Assert.assertEquals(3, result.getStrings().size());
    }

    @Test
    public void testDeserializeBagOfObjects() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WithBagOfValues result = mapper.readerFor(WithBagOfValues.class)
                .readValue("{\"values\": [ \"a\", \"b\", \"c\"]}");
        //        Assert.assertEquals(3, result.getValues().size());
    }

    @Test
    public void testBagOfStringsAlone() throws IOException {
        // curiously, this DOES work too!
        ObjectMapper mapper = new ObjectMapper();
        Bag<String> result = mapper.readerFor(Bag.class)
                .readValue("[ \"a\", \"b\", \"c\"]");
        //        Assert.assertEquals(3, result.size());
    }


}
