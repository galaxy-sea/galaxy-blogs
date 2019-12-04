package com.galaxy.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
        Panda panda = new Panda("132123", "名称", 17);

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
        Panda panda = objectMapper.readValue("{\"phone\":null,\"name\":\"name名称\",\"age\":17,\"nameList\":[\"前缀1\",\"前缀2\",\"前缀3\",\"前缀4\"]}", Panda.class);
        System.out.println(panda);
    }
}
