package io.caojun221.examples.utils;

import java.util.Scanner;

import org.junit.Test;

public class ScannerExampleTest {

    @Test
    public void testScannerWhile() {

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            buffer.append(String.format("%d\n", i));
        }

        String input = buffer.toString();
        Scanner scanner = new Scanner(input);

        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

}