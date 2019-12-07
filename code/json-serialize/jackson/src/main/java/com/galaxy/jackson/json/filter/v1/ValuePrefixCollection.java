package com.galaxy.jackson.json.filter.v1;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author galaxy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = ValuePrefixCollectionJsonSerializer.class)
@JsonDeserialize(using = ValuePrefixCollectionJsonDeserializer.class)
public @interface ValuePrefixCollection {

    String prefix() default "";
}
