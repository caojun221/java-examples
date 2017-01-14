package io.caojun221.examples.crypto;

import java.nio.ByteBuffer;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class MultiPartTest {

    @Test
    public void aesTest() throws Exception {

        byte[] secret = "0123456789abcdef".getBytes("UTF-8");
        System.out.println("secret: " + Arrays.toString(secret));
        Key key = new SecretKeySpec(secret, "AES");

        Cipher encryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        Cipher decryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < 20; i++) {
            byte[] encrypted = encryptionCipher.update(new byte[] { (byte) i });
            byteBuffer.put(encrypted);
            System.out.println(i + " " + Arrays.toString(encrypted));
        }

        byte[] finalBytes = encryptionCipher.doFinal();
        byteBuffer.put(finalBytes);
        System.out.println("final " + Arrays.toString(finalBytes));
        byte[] encryptBytes = new byte[byteBuffer.position()];
        System.arraycopy(byteBuffer.array(), 0, encryptBytes, 0, encryptBytes.length);
        System.out.println("encryptBytes " + Arrays.toString(encryptBytes));

        System.out.println(Arrays.toString(decryptionCipher.update(encryptBytes)));
        System.out.println(Arrays.toString(decryptionCipher.doFinal()));
    }
}
