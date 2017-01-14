package io.caojun221.examples.crypto;

import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class CipherTest {

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    @Test
    public void testAESECB() throws Exception {

        byte[] randomBytes = randomBytes();
        String secret = bytesToHex(randomBytes);
        String encrypted = aesEncryption(secret, "u6aa4cbbd9d81bd4eb0af00017d3759ca.abc");
        System.out.println(encrypted);
        String decrypted = aesDecryption(secret, encrypted);
        System.out.println(decrypted);
    }

    private static String aesDecryption(String secret, String encrypted)
            throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encrypted.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] secretBytes = hexToBytes(secret);
        Key key = new SecretKeySpec(secretBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(decoded), "UTF-8");
    }

    private static String aesEncryption(String secret, String message)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] secretBytes = hexToBytes(secret);
        Key key = new SecretKeySpec(secretBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(Base64.getEncoder().encode(cipher.doFinal(message.getBytes("UTF-8"))), "UTF-8");
    }

    @Test
    public void testHex() {
        byte[] bytes = randomBytes();
        String hex = bytesToHex(bytes);
        byte[] decoded = hexToBytes(hex);
        assertTrue(Arrays.equals(bytes, decoded));
    }

    // Generate a 32 characters hex.
    private static byte[] randomBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(ThreadLocalRandom.current().nextLong());
        byteBuffer.putLong(8, ThreadLocalRandom.current().nextLong());
        return byteBuffer.array();
    }
    private static String bytesToHex(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            hex[i * 2] = HEX_CHARS[(bytes[i] & 0xff) >>> 4];
            hex[i * 2 + 1] = HEX_CHARS[bytes[i] & 0x0F];
        }
        return new String(hex);
    }

    // Convert hex string to byte
    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
