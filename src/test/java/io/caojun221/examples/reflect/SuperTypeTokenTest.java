package io.caojun221.examples.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SuperTypeTokenTest {

    public abstract static class TypeReference<T> {
        private final Type type;
        private volatile Constructor<?> constructor;

        protected TypeReference() {
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof Class) {
                throw new RuntimeException("Missing type parameter");
            }

            type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        }

        public T newInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
                                      InstantiationException {
            if (constructor == null) {
                Class<?> rawType =
                        (Class<?>) (type instanceof Class<?> ? type : ((ParameterizedType) type).getRawType());
                constructor = rawType.getConstructor();
            }
            return (T) constructor.newInstance();
        }
    }


    @Test
    public void testSuperClass()
            throws Exception {
        TypeReference<ArrayList<String>> listTypeReference = new TypeReference<ArrayList<String>>() {};
        List<String> list = listTypeReference.newInstance();

        TypeReference<String> stringTypeReference = new TypeReference<String>() {};
        String str = stringTypeReference.newInstance();
    }
}
