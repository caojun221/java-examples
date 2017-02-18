package io.caojun221.examples.guava;

import java.io.Serializable;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class CollectionTest {

    @Test
    public void test() {
        ImmutableMap<String, Object> map = ImmutableMap.of("k1", 1, "k2", "2");
    }
}
