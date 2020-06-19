package Encryption;

/**
 * Code written by P. Gajland
 * https://github.com/GaPhil
 *
 * IMPORTANT:
 * This code is for educational and demonstrative purpose only.
 * If you need to do serious encryption for "production" it is
 * recommended to investigate more traditional libraries and
 * gain some specific knowledge on cryptography and security.
 */

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.Cipher;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

public class AESenc {

    private String ALGO;
    private byte[] keyValue;
    private boolean initialized = false;


    public AESenc(){
        ALGO = "AES";
        keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String encrypted = new String(Base64.getEncoder().encode(encVal), StandardCharsets.UTF_8);
        return encrypted;
        //return new BASE64Encoder().encode(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        //byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
}
