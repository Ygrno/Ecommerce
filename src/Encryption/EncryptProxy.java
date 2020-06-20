package Encryption;

import Encryption.IEncrypt;

public class EncryptProxy implements IEncrypt {


    private IEncrypt real;

    public EncryptProxy()
    {
        real = null;
    }

    public void setReal(IEncrypt encryptImp){
        this.real = encryptImp;
    }

    @Override
    public boolean init() {
        if(real != null) return real.init();
        else return true;
    }

    @Override
    public String encrypt(String to_encrypt) throws Exception {
                return real.encrypt(to_encrypt);
    }

    @Override
    public String decrypt(String to_decrypt) throws Exception {
        return real.decrypt(to_decrypt);
    }





}
