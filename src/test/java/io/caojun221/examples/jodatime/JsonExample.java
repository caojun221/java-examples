package io.caojun221.examples.jodatime;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JsonExample {

    private static class Bean {
        private DateTime start;

        public DateTime getStart() {
            return start;
        }

        public void setStart(DateTime start) {
            this.start = start;
        }

        @Override
        public String toString() {
            return "Bean{" +
                   "start=" + start +
                   '}';
        }
    }

    @Test
    public void testJodaTimeSerDe() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
        Bean bean = new Bean();
        bean.setStart(DateTime.now());
        String str = mapper.writeValueAsString(bean);
        System.out.println(str);

        Bean deserialized = mapper.readValue(str, Bean.class);
        System.out.println(deserialized);
    }

}
