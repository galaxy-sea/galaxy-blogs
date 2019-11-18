package com.galaxy.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.jackson.bean.Panda;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
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
        panda.setData(data);

        String json = objectMapper.writeValueAsString(panda);
        System.out.println(json);
    }

    @Test
    public void JsonDeserializer() throws JsonProcessingException {
        Panda panda = objectMapper.readValue("{\"phone\":\"................132123\",\"name\":null,\"age\":17}", Panda.class);
        System.out.println(panda);
    }

}
