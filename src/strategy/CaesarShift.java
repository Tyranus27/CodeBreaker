package strategy;

/**
 * @author krawi
 * @version 1
 * Class performs Caesar shift cipher
 */
public class CaesarShift extends CipherData implements Cipher {

    @Override
    public String encrypt(String plainText, String key) {
        return performCalculation(plainText, encryptionKey(key));
    }

    @Override
    public String decrypt(String cipherText, String key) {
        return performCalculation(cipherText, decryptionKey(key));
    }

    @Override
    public String getName() {
        return "Caesar shift";
    }

    private int encryptionKey(String key) {
        return Integer.parseInt(key.trim());
    }

    private int decryptionKey(String key) {
        return 26 - (Integer.parseInt(key.trim()) % 26);
    }

    private String performCalculation(String text, int fixedKey) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int orginalAlphPos = character - 'A';
            int newAlphPos = (orginalAlphPos + fixedKey) % getAlphabet().length;
            char newChar = (char) ('A' + newAlphPos);
            result.append(newChar);
        }
        return result.toString();
    }
}
