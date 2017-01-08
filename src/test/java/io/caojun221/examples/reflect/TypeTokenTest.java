package io.caojun221.examples.reflect;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class TypeTokenTest {

    /**
     * type safe by Class class.
     * https://docs.oracle.com/javase/tutorial/extra/generics/literals.html
     */
    @Test
    public void testNewInstance() throws Exception {
        Collection<String> list = select(String.class, 100);
        System.out.println(list);
    }

    private static <T> Collection<T> select(Class<T> c, int size)
            throws IllegalAccessException, InstantiationException {
        Collection<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(c.newInstance());
        }
        return result;
    }


    @Test
    public void test() throws Exception {
        String a = "a";
        Class<? extends String> clazz = a.getClass();

        String b = clazz.newInstance();
        System.out.println(b);
    }
}
