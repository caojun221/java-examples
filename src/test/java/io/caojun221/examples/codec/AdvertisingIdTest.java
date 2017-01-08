package io.caojun221.examples.codec;

import java.nio.ByteBuffer;

import org.junit.Test;

public class AdvertisingIdTest {

    private static final char[] HEX = "0123456789abcdef".toCharArray();

    @Test
    public void testAdvertisingIdFromInt() {
        System.out.println(advertisingIdFromInt(1));
    }

    public String advertisingIdFromInt(int value) {
        // 16 bytes
        byte[] bytes = ByteBuffer.allocate(16).putInt(12, value).array();

        char[] result = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            byte v = (byte) (bytes[i] & 0xff);
            result[i * 2] = HEX[v >>> 4];
            result[i * 2 + 1] = HEX[v & 0x0F];
        }
        String hexStr = new String(result);
        return hexStr.substring(0, 8) + '-' + hexStr.substring(8, 12) + '-' + hexStr.substring(12, 16) + '-' +
               hexStr.substring(16, 20) + '-' + hexStr.substring(20, hexStr.length());
    }
}
