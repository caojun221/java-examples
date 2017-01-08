package io.caojun221.examples.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Test;

public class TypeTest {

    public static class RegularClass {
        public RegularClass() {
            System.out.println("superclass: " + getClass().getClass());
        }
    }

    public static class GenericClass<T> {

        private final Type superclass;

        public GenericClass() {
            superclass = getClass().getGenericSuperclass();
            System.out.println("superclass: " + superclass.getClass());
        }
    }

    public abstract static class AbstractGenericClass<K, V> {

        private final Type superclass;

        protected AbstractGenericClass() {
            superclass = getClass().getGenericSuperclass();
            System.out.println("superclass: " + superclass.getClass());

            if (superclass instanceof ParameterizedType) {
                Type[] actualTypes = ((ParameterizedType) superclass).getActualTypeArguments();
                for (Type actualType : actualTypes) {
                    System.out.println("actual type: " + actualType);
                }
            }
        }
    }

    @Test
    public void test() {
        new RegularClass();
        new GenericClass<String>();
        new AbstractGenericClass<String, Integer>() {};
    }
}
