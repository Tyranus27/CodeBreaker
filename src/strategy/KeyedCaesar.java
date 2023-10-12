package strategy;

import java.util.*;

/**
 * @author krawi
 * @version 1
 * Class performs caesar shift with keyword
 */

public class KeyedCaesar extends CipherData implements Cipher {

    private List<Character> shiftedAlphabet;
    private List<Character> keyedAlphabet;
    private StringBuilder result;
    private String keyword;
    private int number;

    @Override
    public String encrypt(String plainText, String key) {
        prepareCipherData(key);

        for (char characterOfPlainText : plainText.toCharArray()) {
            if (shiftedAlphabet.contains(characterOfPlainText)) {
                int i = shiftedAlphabet.indexOf(characterOfPlainText);
                result.append(keyedAlphabet.get(i));
            }
        }

        return result.toString();
    }

    @Override
    public String decrypt(String cipherText, String key) {
        prepareCipherData(key);

        for (char characterOfTheCipherText : cipherText.toCharArray()){
            if (keyedAlphabet.contains(characterOfTheCipherText)){
                int i = keyedAlphabet.indexOf(characterOfTheCipherText);
                result.append(shiftedAlphabet.get(i));
            }
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Keyed Caesar Shift";
    }

    private void prepareCipherData(String key) {
        extractKey(key);
        shiftedAlphabet = shiftAlphabet();
        keyedAlphabet = createKeyedAlphabet();
        result = new StringBuilder();

    }

    private void extractKey(String key) {
        String[] splitter = key.split("(?<=\\d)(?=\\D)");
        keyword = splitter[1];
        number = Integer.parseInt(splitter[0]);
    }

    private List<Character> createKeyedAlphabet() {
        List<Character> alphabetArray = createAlphabetArray();
        List<Character> keyedAlphabet = new ArrayList<>();
        char[] chars1 = keyword.toUpperCase().toCharArray();
        for (char letter : chars1) {
            if (!keyedAlphabet.contains(letter)) {
                keyedAlphabet.add(letter);
            }
        }
        List<Character> alphabetWithoutKeyLetters = new ArrayList<>();
        for (Character alphabetLetter : alphabetArray) {
            if (!keyedAlphabet.contains(alphabetLetter)) {
                alphabetWithoutKeyLetters.add(alphabetLetter);
            }
        }
        keyedAlphabet.addAll(alphabetWithoutKeyLetters);
        return keyedAlphabet;
    }

    private List<Character> createAlphabetArray() {
        List<Character> alphabetArray = new ArrayList<>();
        for (char letter : getAlphabet()) {
            alphabetArray.add(letter);
        }
        return alphabetArray;
    }

    private List<Character> shiftAlphabet() {
        List<Character> shiftedAlphabet = new ArrayList<>();
        for (char character : getAlphabet()) {
            int orginalAlphPos = character - 'A';
            int newAlphPos = (orginalAlphPos + (26 - number)) % 26;
            char newChar = (char) ('A' + newAlphPos);
            shiftedAlphabet.add(newChar);
        }
        return shiftedAlphabet;
    }
}
