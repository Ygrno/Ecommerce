package Encryption;

import Encryption.AESenc;
import Encryption.IEncrypt;

public class EncryptReal implements IEncrypt {

    AESenc aeSenc = new AESenc();

    @Override
    public boolean init() {
        return aeSenc.isInitialized();
    }

    @Override
    public String encrypt(String to_encrypt) throws Exception {
        return aeSenc.encrypt(to_encrypt);
    }

    @Override
    public String decrypt(String to_decrypt) throws Exception {
        return aeSenc.decrypt(to_decrypt);
    }


}
