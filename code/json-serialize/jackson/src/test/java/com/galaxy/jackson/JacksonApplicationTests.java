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
        data.add("v1");
        data.add("v1v1");
        data.add("v1v1v1");
        data.add("v1v1v1v1");
        panda.setNameListV1(data);
        data = new ArrayList<String>();
        data.add("v2");
        data.add("v2v2");
        data.add("v2v2v2");
        data.add("v2v2v2v2");
        panda.setNameListV2(data);

        String json = objectMapper.writeValueAsString(panda);
        System.out.println(json);
    }

    @Test
    public void JsonDeserializer() throws JsonProcessingException {
        Panda panda = objectMapper.readValue("{\"phone\":\"phone132123\",\"name\":\"name名称\",\"age\":17,\"nameListV1\":[\"nameListV11\",\"nameListV12\",\"nameListV13\",\"nameListV14\"],\"nameListV2\":[\"nameListV21\",\"nameListV22\",\"nameListV23\",\"nameListV24\"]}", Panda.class);
        System.out.println(panda);
    }
}
