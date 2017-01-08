package io.caojun221.examples.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TypeReferenceTest {

    @Test
    public void test() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> obj = objectMapper.readValue("[]", new TypeReference<List<String>>() {});
        System.out.println(obj);
        //noinspection unchecked
        obj = objectMapper.readValue("[]", ArrayList.class);
        System.out.println(obj);
        //noinspection InstantiatingObjectToGetClassObject
        obj = objectMapper.readValue("[]", new ArrayList<String>().getClass());
        System.out.println(obj);
    }
}
