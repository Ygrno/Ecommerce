package Unit.tests;

import Encryption.AESenc;
import org.junit.Test;

public class EncryptionTest {

    AESenc aeSenc = new AESenc();

    private final String message = "Password";
    private String encrypted;

    @Test
    public void encrypt() throws Exception {
        encrypted = aeSenc.encrypt(message);
        assert (encrypted != null && !encrypted.equals(""));
    }

    @Test
    public void decrypt() throws Exception {
        encrypted = aeSenc.encrypt(message);
        String decrypted = aeSenc.decrypt(encrypted);
        assert(decrypted.equals(message));
    }

}
