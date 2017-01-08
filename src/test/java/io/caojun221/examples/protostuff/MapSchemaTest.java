package io.caojun221.examples.protostuff;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.protostuff.LinkedBuffer;
import io.protostuff.MessageMapSchema;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.runtime.RuntimeSchema;

public class MapSchemaTest {

    @Test
    public void test() {
        MessageMapSchema<Long, String> messageMapSchema = new MessageMapSchema<>(
                RuntimeSchema.getSchema(Long.class), RuntimeSchema.getSchema(String.class));

        Map<Long, String> map = new HashMap<>();
        map.put(1L, "1");

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] bytes = ProtobufIOUtil.toByteArray(map, messageMapSchema, buffer);

        Map<Long, String> resultMap = messageMapSchema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, resultMap, messageMapSchema);

        assertEquals(map, resultMap);
    }
}
