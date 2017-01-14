package io.caojun221.examples.messagepack;

import java.io.IOException;
import java.util.Objects;

import org.junit.Test;
import org.msgpack.MessagePack;

public class SerDeTest {

    public static class Bean {
        private final String stringField;
        private final int intField;

        public Bean(String stringField, int intField) {
            this.stringField = stringField;
            this.intField = intField;
        }

        public Bean() {
            intField = 0;
            stringField = null;
        }

        public String getStringField() {
            return stringField;
        }

        public int getIntField() {
            return intField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            Bean bean = (Bean) o;
            return intField == bean.intField &&
                   Objects.equals(stringField, bean.stringField);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stringField, intField);
        }

        @Override
        public String toString() {
            return "Bean{" +
                   "stringField='" + stringField + '\'' +
                   ", intField=" + intField +
                   '}';
        }
    }

    @Test
    public void test() throws IOException {

        MessagePack messagePack = new MessagePack();
        messagePack.register(Bean.class);
        Bean bean = new Bean("mid", 1);
        byte[] bytes = messagePack.write(bean);
//
        String deserialized = messagePack.read(bytes, String.class);
        System.out.println(deserialized);
    }
}
