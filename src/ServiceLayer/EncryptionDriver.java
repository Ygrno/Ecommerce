package ServiceLayer;

import Encryption.EncryptProxy;
import Encryption.EncryptReal;
import Encryption.IEncrypt;

public abstract class EncryptionDriver {

    public static EncryptProxy getEncryption() {
        EncryptProxy proxy = new EncryptProxy();
        EncryptReal real = new EncryptReal();

        proxy.setReal(real);
        return proxy;
    }

}
