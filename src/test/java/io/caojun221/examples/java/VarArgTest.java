package io.caojun221.examples.java;

import java.util.Arrays;
import java.util.Objects;

import org.junit.Test;

public class VarArgTest {

    @Test
    public void test() {
        foo((String) null);
        foo(null, null);
        foo(null);
    }

    private static void foo(String... args) {
        System.out.println(Arrays.toString(Objects.requireNonNull(args)));
    }
}
