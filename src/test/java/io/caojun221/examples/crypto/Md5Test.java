package io.caojun221.examples.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Md5Test {

    @Test
    public void testUpdate() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((byte) 'a');
        md5.update((byte) 'b');
        md5.update(new byte[] { (byte) 'c' }, 0, 1);
    }
}
