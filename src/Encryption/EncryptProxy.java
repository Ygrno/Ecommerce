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
    public String encrypt(String to_encrypt) {
        if(real != null) {
            try {
                return real.encrypt(to_encrypt);
            } catch (Exception e) {
                e.printStackTrace();
                return to_encrypt;
            }
        }
        else return to_encrypt;
    }

    @Override
    public String decrypt(String to_decrypt) {
        if(real != null) {
            try {
                return real.decrypt(to_decrypt);
            } catch (Exception e) {
                e.printStackTrace();
                return to_decrypt;
            }
        }
        else return to_decrypt;
    }





}
