package io.caojun221.examples.pattern;

import java.util.regex.Pattern;

import org.junit.Test;

public class SplitTest {


    private static final Pattern COMMA_SPLIT = Pattern.compile("\\s*,\\s*");

    @Test
    public void testSpaceComma() {
        String[] result = COMMA_SPLIT.split("1, 2 ,3 , 4,");
        for (String s : result) {
            System.out.println(s);
        }
    }
}
