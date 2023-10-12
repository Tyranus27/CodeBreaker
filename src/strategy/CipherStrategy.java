package strategy;

/**
 * @author krawi
 * @version 1
 * Class contains method for cipher interface
 */
public class CipherStrategy {
private Cipher cipher;

    public CipherStrategy(Cipher cipher) {
        this.cipher = cipher;
    }

    public String cipher(String plainText,String key) {
        return cipher.encrypt(plainText, key);

    }
    public String decipher (String cipherText, String key){
        return cipher.decrypt(cipherText, key);
    }

    public String getName() {
        return cipher.getName();
    }

    public Cipher getCipher() {
        return this.cipher;
    }
}

