package strategy;

/**
 * @author krawi
 * @version 1
 * Interface runs encrypting or decrypting on one of three ciphers
 */

public interface Cipher  {



    String encrypt(String plainText, String key);

    String decrypt(String cipherText, String key);

    String getName();


}
