package Encryption;

public interface IEncrypt {

    public boolean init();
    public String encrypt(String to_encrypt) throws Exception;
    public String decrypt(String to_decrypt) throws Exception;

}
