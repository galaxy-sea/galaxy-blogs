
<!-- TOC -->

- [1. 序列化](#1-%e5%ba%8f%e5%88%97%e5%8c%96)
  - [1.1. JsonSerializer<T>序列化父类](#11-jsonserializert%e5%ba%8f%e5%88%97%e5%8c%96%e7%88%b6%e7%b1%bb)
  - [1.2. ContextualSerializer序列化上下文](#12-contextualserializer%e5%ba%8f%e5%88%97%e5%8c%96%e4%b8%8a%e4%b8%8b%e6%96%87)
  - [1.3. 自定义序列化注册Jackson](#13-%e8%87%aa%e5%ae%9a%e4%b9%89%e5%ba%8f%e5%88%97%e5%8c%96%e6%b3%a8%e5%86%8cjackson)
    - [1.3.1. SimpleModule](#131-simplemodule)
    - [1.3.2. @JsonSerialize 注解](#132-jsonserialize-%e6%b3%a8%e8%a7%a3)
    - [1.3.3. CustomSerializerFactory](#133-customserializerfactory)
- [2. 反序列化](#2-%e5%8f%8d%e5%ba%8f%e5%88%97%e5%8c%96)
  - [2.1. JsonDeserializer<T>序列化父类](#21-jsondeserializert%e5%ba%8f%e5%88%97%e5%8c%96%e7%88%b6%e7%b1%bb)
  - [2.2. ContextualDeserializer反序列化上下文](#22-contextualdeserializer%e5%8f%8d%e5%ba%8f%e5%88%97%e5%8c%96%e4%b8%8a%e4%b8%8b%e6%96%87)
  - [2.3. 反序列化注册](#23-%e5%8f%8d%e5%ba%8f%e5%88%97%e5%8c%96%e6%b3%a8%e5%86%8c)
    - [2.3.1. SimpleModule](#231-simplemodule)
    - [2.3.2. @Deserializer注解](#232-deserializer%e6%b3%a8%e8%a7%a3)
    - [2.3.3. CustomDeserializerFactory](#233-customdeserializerfactory)
- [3. 实践操作](#3-%e5%ae%9e%e8%b7%b5%e6%93%8d%e4%bd%9c)
- [4. JsonSerializer](#4-jsonserializer)
  - [4.1. 自定义StringJsonSerializer](#41-%e8%87%aa%e5%ae%9a%e4%b9%89stringjsonserializer)
  - [4.2. 自定义CollectionJsonSerializer](#42-%e8%87%aa%e5%ae%9a%e4%b9%89collectionjsonserializer)
  - [4.3. 自定义StringArraySerializer](#43-%e8%87%aa%e5%ae%9a%e4%b9%89stringarrayserializer)
  - [4.4. 自定义MapJsonSerializer](#44-%e8%87%aa%e5%ae%9a%e4%b9%89mapjsonserializer)
    - [4.4.1. _keySerializer](#441-keyserializer)
    - [4.4.2. _valueSerializer](#442-valueserializer)
  - [4.5. ValuePrefixJsonSerializerFactory](#45-valueprefixjsonserializerfactory)
- [5. 反序列化](#5-%e5%8f%8d%e5%ba%8f%e5%88%97%e5%8c%96)
  - [5.1. ValuePrefixJsonDeserializerFactory](#51-valueprefixjsondeserializerfactory)
- [6. 参考](#6-%e5%8f%82%e8%80%83)

<!-- /TOC -->

> 源码放在了GitHub,  地址: https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/json-serialize
> 
> 源码放在了GitHub,  地址: https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/json-serialize
> 
> 源码放在了GitHub,  地址: https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/json-serialize



# 1. 序列化
## 1.1. JsonSerializer<T>序列化父类
> 在 jackson 自定义序列化中提供了两个抽象类，``com.fasterxml.jackson.databind.JsonSerializer``和``com.fasterxml.jackson.databind.ser.std.StdSerializer``。一般，我们可以直接继承``JsonSerializer``抽象类就已经足够满足我们的自定义序列化需求了。
> 
> 下面我们看一下jackson的``StringSerializer``源码, 发现自定义序列化是如此的简单,基本没有什么难受难度
```java
@JacksonStdImpl
public final class StringSerializer extends StdScalarSerializer<Object>{

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString((String) value);
    }

    ...
}
``` 

## 1.2. ContextualSerializer序列化上下文
> 在序列化的时候我们难免需要获取字段或者类的一些信息(字段名, 字段类型, 字段注解, 类名, 类注解 ...)这个使用我们就需要使用jackson提供的
``com.fasterxml.jackson.databind.ser.ContextualSerializer``
> 
> 下面我们看一下``com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase``的源码, 看他对``com.fasterxml.jackson.annotation.JsonFormat``注解的操作
> ``com.fasterxml.jackson.annotation.JsonFormat``是平常开发中对序列化进行格式化

```java
public abstract class DateTimeSerializerBase<T> extends StdScalarSerializer<T> implements ContextualSerializer{
@Override
    public JsonSerializer<?> createContextual(SerializerProvider serializers,
            BeanProperty property) throws JsonMappingException
    {
        // Note! Should not skip if `property` null since that'd skip check
        // for config overrides, in case of root value
        JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
        if (format == null) {
            return this;
        }
        // Simple case first: serialize as numeric timestamp?
        JsonFormat.Shape shape = format.getShape();
        if (shape.isNumeric()) {
            return withFormat(Boolean.TRUE, null);
        }

        // 08-Jun-2017, tatu: With [databind#1648], this gets bit tricky..
        // First: custom pattern will override things
        if (format.hasPattern()) {
            final Locale loc = format.hasLocale() ? format.getLocale() : serializers.getLocale();
            SimpleDateFormat df = new SimpleDateFormat(format.getPattern(), loc);
            TimeZone tz = format.hasTimeZone() ? format.getTimeZone() : serializers.getTimeZone();
            df.setTimeZone(tz);
            return withFormat(Boolean.FALSE, df);
        }

        // Otherwise, need one of these changes:
        final boolean hasLocale = format.hasLocale();
        final boolean hasTZ = format.hasTimeZone();
        final boolean asString = (shape == JsonFormat.Shape.STRING);

        if (!hasLocale && !hasTZ && !asString) {
            return this;
        }

        DateFormat df0 = serializers.getConfig().getDateFormat();
        // Jackson's own `StdDateFormat` is quite easy to deal with...
        if (df0 instanceof StdDateFormat) {
            StdDateFormat std = (StdDateFormat) df0;
            if (format.hasLocale()) {
                std = std.withLocale(format.getLocale());
            }
            if (format.hasTimeZone()) {
                std = std.withTimeZone(format.getTimeZone());
            }
            return withFormat(Boolean.FALSE, std);
        }

        // 08-Jun-2017, tatu: Unfortunately there's no generally usable
        //    mechanism for changing `DateFormat` instances (or even clone()ing)
        //    So: require it be `SimpleDateFormat`; can't config other types
        if (!(df0 instanceof SimpleDateFormat)) {
             serializers.reportBadDefinition(handledType(), String.format("Configured `DateFormat` (%s) not a `SimpleDateFormat`; cannot configure `Locale` or `TimeZone`", df0.getClass().getName()));
        }
        SimpleDateFormat df = (SimpleDateFormat) df0;
        if (hasLocale) {
            // Ugh. No way to change `Locale`, create copy; must re-crete completely:
            df = new SimpleDateFormat(df.toPattern(), format.getLocale());
        } else {
            df = (SimpleDateFormat) df.clone();
        }
        TimeZone newTz = format.getTimeZone();
        boolean changeTZ = (newTz != null) && !newTz.equals(df.getTimeZone());
        if (changeTZ) {
            df.setTimeZone(newTz);
        }
        return withFormat(Boolean.FALSE, df);
    }

}

 ......

```

## 1.3. 自定义序列化注册Jackson
虽然注册的方式很多，但是主要是三种
- SimpleModule
- @JsonSerialize(推荐)
- CustomSerializerFactory(新版已删除)

### 1.3.1. SimpleModule
``com.fasterxml.jackson.databind.module.SimpleModule``的注册太麻烦了
```java
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Panda.class, new PandSerializer);
    mapper.registerModule(module);
    String jsonString = mapper.writeValueAsString(new Panda());
```

### 1.3.2. @JsonSerialize 注解
通过注解的方式注册序列化方法，对使用体验来说非常友好。但是会存在两个问题：<br>
首先，提供的序列化类的功能比较简单，然后，就是提供的粒度比较小，然后你想在全局的某个类型上都是有自定义序列化，则需要对每个对象类型上都需要标记注解。

@JsonSerialize 注解的配置参数有很多种，但是我们只需要using这种来指明具体的自定义序列化类就可以了。

### 1.3.3. CustomSerializerFactory
```java
后面章节手动实现一个SerializerFactory
```

# 2. 反序列化
反序列化基本上和序列化差不多
## 2.1. JsonDeserializer<T>序列化父类
> 自定反义序列化中提供了两个抽象类，``com.fasterxml.jackson.databind.JsonDeserializer``和``com.fasterxml.jackson.databind.deser.std.StdDeserializer``。一般，我们可以直接继承``JsonDeserializer``抽象类就已经足够满足我们的自定义反序列化需求了。
> 
> 下面我们看一下jackson的``StringDeserializer``源码, 反序列化比序列化要复杂一点
```java
public class StringDeserializer extends StdScalarDeserializer<String>{
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            return p.getText();
        }
        JsonToken t = p.getCurrentToken();
        // [databind#381]
        if (t == JsonToken.START_ARRAY) {
            return _deserializeFromArray(p, ctxt);
        }
        // need to gracefully handle byte[] data, as base64
        if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
            Object ob = p.getEmbeddedObject();
            if (ob == null) {
                return null;
            }
            if (ob instanceof byte[]) {
                return ctxt.getBase64Variant().encode((byte[]) ob, false);
            }
            // otherwise, try conversion using toString()...
            return ob.toString();
        }
        // allow coercions for other scalar types
        // 17-Jan-2018, tatu: Related to [databind#1853] avoid FIELD_NAME by ensuring it's
        //   "real" scalar
        if (t.isScalarValue()) {
            String text = p.getValueAsString();
            if (text != null) {
                return text;
            }
        }
        return (String) ctxt.handleUnexpectedToken(_valueClass, p);
    }

    ......
}
```
## 2.2. ContextualDeserializer反序列化上下文
查看``com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase``对``com.fasterxml.jackson.annotation.JsonFormat``的上下文关系
```java
public abstract class JSR310DateTimeDeserializerBase<T> extends JSR310DeserializerBase<T> implements ContextualDeserializer{
    
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException{
        JsonFormat.Value format = findFormatOverrides(ctxt, property, handledType());
        JSR310DateTimeDeserializerBase<?> deser = this;
        if (format != null) {
            if (format.hasPattern()) {
                final String pattern = format.getPattern();
                final Locale locale = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
                if (acceptCaseInsensitiveValues(ctxt, format)) {
                    builder.parseCaseInsensitive();
                }
                builder.appendPattern(pattern);
                DateTimeFormatter df;
                if (locale == null) {
                    df = builder.toFormatter();
                } else {
                    df = builder.toFormatter(locale);
                }
                //Issue #69: For instant serializers/deserializers we need to configure the formatter with
                //a time zone picked up from JsonFormat annotation, otherwise serialization might not work
                if (format.hasTimeZone()) {
                    df = df.withZone(format.getTimeZone().toZoneId());
                }
                deser = deser.withDateFormat(df);
            }
            // 17-Aug-2019, tatu: For 2.10 let's start considering leniency/strictness too
            if (format.hasLenient()) {
                Boolean leniency = format.getLenient();
                if (leniency != null) {
                    deser = deser.withLeniency(leniency);
                }
            }
            // any use for TimeZone?
        }
        return deser;
    }
    
}
```


## 2.3. 反序列化注册

### 2.3.1. SimpleModule
``com.fasterxml.jackson.databind.module.SimpleModule``的注册太麻烦了
```java
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Panda.class, new PandDeserializer);
        mapper.registerModule(module);
        Panda panda = objectMapper.readValue(jsonString, Panda.class);
```

### 2.3.2. @Deserializer注解
通过注解的方式注册序列化方法，对使用体验来说非常友好。但是会存在两个问题：<br>
首先，提供的序列化类的功能比较简单，然后，就是提供的粒度比较小，然后你想在全局的某个类型上都是有自定义序列化，则需要对每个对象类型上都需要标记注解。

@JsonDeserializer 注解的配置参数有很多种，但是我们只需要using这种来指明具体的自定义序列化类就可以了。

### 2.3.3. CustomDeserializerFactory
```java
后面章节手动实现一个DeserializerFactory
```

# 3. 实践操作

> 业务分析, 只要字段上面注解了``@ValuePrefix``注解,就需要序列化时让``value``前加上前缀,<br>
> 所以我们需要定义一个``@ValuePrefix``注解用于注册``JsonSerialize``和``JsonDeserialize``<br>
> 要需要定义一个bean用于序列化和反序列化, 详细代码如下



```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})

// 指定序列化
@JsonSerialize(using = ValuePrefixJsonSerializerFactory.class)
// 指定反序列化
@JsonDeserialize(using = ValuePrefixJsonDeserializerFactory.class)
public @interface ValuePrefix {
    String prefix() default "前缀";
}
```

```java
public class Panda {

    @ValuePrefix(prefix = "img前缀")
    private String img;
    
    @ValuePrefix(prefix = "imgList前缀")
    private List<String> imgList;

    @ValuePrefix(prefix = "imgArray前缀")
    private String[] imgArray;

    @ValuePrefix(prefix = "imgMap前缀")
    private Map<String,String> imgMap;

    // getter and setter
}
```

# 4. JsonSerializer

## 4.1. 自定义StringJsonSerializer
jackson 提供了一个``String``序列化类``com.fasterxml.jackson.databind.ser.std.StringSerializer``, 但是``StringSerializer``类被``final``修饰了不能被继承, 但是无妨我们学习, 我们可以查看``StringSerializer.serialize()``方法,惊奇的发现, 我草这个方法好简单,

下面我们就自定义一个StringJsonSerializer类

```java
    /**
    * @author galaxy
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
```
偷懒的第一步, 简直完美呀, 这里我们就对自定义的String序列化完成了,  没有什么代码含量是不是, 直接看源码复制一下就可以


## 4.2. 自定义CollectionJsonSerializer
上面我们偷懒直接复制了jackson的``StringSerializer``, 对于集合的序列化当然jackson也是提供的
我们可以查看``com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer``, ``StringCollectionSerializer``是没有被``final``修饰的我们可以直接继承,当然也可以不继承
```java
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
```
其实序列化还是很简单的, 



## 4.3. 自定义StringArraySerializer
学习 ``com.fasterxml.jackson.databind.ser.impl.StringArraySerializer`` 基本上和``com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer``一样

```java
// 懒得写了
```

## 4.4. 自定义MapJsonSerializer
学习``com.fasterxml.jackson.databind.ser.std.MapSerializer``和``com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer``这两个类, 
阅读源码你会发现需要自定义``_keySerializer``和``_valueSerializer``

```java
// 不想看源码了,不想写
```
### 4.4.1. _keySerializer
```java
// 不想看源码了,不想写
```
### 4.4.2. _valueSerializer
```java
// 不想看源码了,不想写
```


## 4.5. ValuePrefixJsonSerializerFactory
到这里你就发现了, bean只能指定一个序列化对象, 不能指定多个序列化对象, 一个注解很难满足多个类型序列化, 这个时候我们就需要``SerializerFactory``来帮助我们选择合适的序列化了, 下面我们就来动手实现一个,
同时优化一下上面的代码, <br>

在定义的@ValuePrefix注册的序列化类是``ValuePrefixJsonSerializerFactory``, 

我们上面自己定义的``JsonSerializerFactory``对象都定义为``ValuePrefixJsonSerializerFactory``内部类

``ContextualSerializer.createContextual()``方法有个很有趣的地方那就是它的返回值居然是``JsonSerializer``, 所以在获取上下文的时候我们可以返回相应的``JsonSerializer``


```java
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
        throw new JsonMappingException("不支持的类型,  仅支持 String, Collection");
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
```
到这里我们的序列化工作就完成了, 我就讲讲反序列化吧, 


# 5. 反序列化
序列化和反序列化车操作差不多,大家可以自己看源码操作, 下面直接贴出反序列化的源代码吧

## 5.1. ValuePrefixJsonDeserializerFactory

```java
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
        throw new JsonMappingException("不支持的类型,  仅支持 String, Collection");
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
```


# 6. 参考
- https://yanbin.blog/customize-jackson-annotation-and-disable-specific-annotation/
- https://blog.csdn.net/liuxiao723846/article/details/42921333
- https://stackoverflow.com/questions/43214545/customdeserializer-has-no-default-no-arg-constructor
- https://codeday.me/bug/20190516/1112991.html
- https://ketao1989.github.io/2015/02/10/Jackson-Deserialize-Java-Object-Implementation/
- https://github.com/FasterXML/jackson-databind/issues/2324
- https://stackoverflow.com/questions/8944086/custom-jackson-deserializer-getting-access-to-current-field-class