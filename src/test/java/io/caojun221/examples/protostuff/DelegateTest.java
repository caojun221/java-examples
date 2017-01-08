package io.caojun221.examples.protostuff;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import io.protostuff.Input;
import io.protostuff.LinkedBuffer;
import io.protostuff.Output;
import io.protostuff.Pipe;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.WireFormat.FieldType;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.Delegate;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

public class DelegateTest {

    private static final class URIDelegate implements Delegate<URI> {

        @Override
        public FieldType getFieldType() {
            return FieldType.STRING;
        }

        @Override
        public URI readFrom(Input input) throws IOException {
            try {
                return new URI(input.readString());
            } catch (URISyntaxException e) {
                throw new IOException(e);
            }
        }

        @Override
        public void writeTo(Output output, int number, URI value, boolean repeated) throws IOException {
            output.writeString(number, value.toString(), repeated);
        }

        @Override
        public void transfer(Pipe pipe, Input input, Output output, int number, boolean repeated)
                throws IOException {

        }

        @Override
        public Class<?> typeClass() {
            return URI.class;
        }
    }

    private static class Bean {
        private URI uri;

        public URI getUri() {
            return uri;
        }

        public void setUri(URI uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "Bean{" +
                   "uri=" + uri +
                   '}';
        }
    }

    @Test
    public void test() throws URISyntaxException {
        DefaultIdStrategy idStrategy = (DefaultIdStrategy) RuntimeEnv.ID_STRATEGY;
        idStrategy.registerDelegate(new URIDelegate());
        Schema<Bean> schema = RuntimeSchema.createFrom(Bean.class, idStrategy);

        Bean bean = new Bean();
        URI uri = new URI("http://www.example.com");
        bean.setUri(uri);

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] bytes = ProtobufIOUtil.toByteArray(bean, schema, buffer);

        Bean deserialized = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, deserialized, schema);

        assertEquals(uri, bean.getUri());
    }
}
