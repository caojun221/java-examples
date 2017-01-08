package io.caojun221.examples.bit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShiftTest {

    /**
     * http://stackoverflow.com/questions/3948220/behaviour-of-unsigned-right-shift-applied-to-byte-variable
     * byte variable is promoted to int prior to shift operation, masking with & 0xff on byte variable first.
     */
    @Test
    public void byteUnsignedShift() {

        // 11111110
        byte toShift = -127;

        // 00001111
        byte shifted = (byte) ((toShift & 0xff) >>> 4);

        assertEquals(8, shifted);
    }

    /**
     * -128:             1111111111111111111111111000000
     * -128 >> 4:        1111111111111111111111111111000
     * -128 >>> 4:       0000111111111111111111111111000
     * -128 & 0xff >>>4: 0000000000000000000000000001000
     */
    @Test
    public void testShift() {
        shift((byte)-128);
    }

    private static void shift(byte value) {
        System.out.println(Integer.toBinaryString(value));
        System.out.println(Integer.toBinaryString(value >> 4));
        System.out.println(Integer.toBinaryString(value >>> 4));
        System.out.println(Integer.toBinaryString((value & 0xff) >>> 4));
    }
}
