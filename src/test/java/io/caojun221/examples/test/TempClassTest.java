package io.caojun221.examples.test;

import org.junit.Test;

public class TempClassTest {

    @Test
    public void testMethod1() throws Exception {
        TempClass tempClass = new TempClass();
        tempClass.methodA(true);
        tempClass.methodA(false);
    }
}