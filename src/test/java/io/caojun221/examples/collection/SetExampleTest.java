package io.caojun221.examples.collection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SetExampleTest {

    @Test
    public void testNewSetFromList() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 1, 1));
        Set<Integer> set = new HashSet<>(list);
        assertEquals(1, set.size());
    }
}