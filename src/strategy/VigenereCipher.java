package strategy;

/**
 * @author krawi
 * @version 1
 * Class performs ciphering with vigenere cipher method
 */

public class VigenereCipher extends CipherData implements Cipher {

    private char[][] grid;

    @Override
    public String encrypt(String plainText, String key) {
        return performEncryption(plainText, key);
    }

    @Override
    public String decrypt(String cipherText, String key) {
        return performDecryption(cipherText, key);
    }

    @Override
    public String getName() {
        return "Vigenere Cipher";
    }

    private String performEncryption(String text, String key) {
        initializeGrid();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.toCharArray().length; i++) {
            int columnPosition = text.toCharArray()[i] - 'A';
            String preparedKey = preparingKey(text, key).toUpperCase();
            int rowPosition = preparedKey.toCharArray()[i] - 'A';
            result.append(grid[rowPosition][columnPosition]);
        }
        return result.toString();
    }

    private String performDecryption(String text, String key) {
        initializeGrid();
        StringBuilder result = new StringBuilder();
        String preparedKey = preparingKey(text, key).toUpperCase();

        for (int i = 0; i < preparedKey.toCharArray().length; i++) {
            int position = (text.charAt(i) - preparedKey.charAt(i) + 26) % 26;
            char newChar = (char) (position + 'A');
            result.append(newChar);
        }
        return result.toString();
    }

    private void initializeGrid() {
        grid = new char[26][26];
        for (int i = 0; i < getAlphabet().length; i++) {
            for (int j = 0; j < getAlphabet().length; j++) {
                int originalPos = getAlphabet()[j] - 'A';
                int newAlphabe = (originalPos + i) % getAlphabet().length;
                char newChar = (char) ('A' + newAlphabe);
                grid[i][j] = newChar;
            }
        }
    }

    private String preparingKey(String plainText, String key) {
        StringBuilder keyBuilder = new StringBuilder(key);
        for (int i = 0; ; i++) {
            if (plainText.length() == i) {
                i = 0;
            } else if (keyBuilder.length() == plainText.length()) {
                break;
            }
            keyBuilder.append(keyBuilder.charAt(i));
        }
        key = keyBuilder.toString().toUpperCase();
        return key.toUpperCase();
    }
}

