package strategy;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author krawi
 * @version 1
 * Class CipherMenu run whole program
 */
public class CipherMenu {

    private Scanner scan;
    private String filename;
    private String recentPlainText;
    private String recentKey;
    private String recentCipherText;
    private CipherStrategy recentStrategy = new CipherStrategy(new CaesarShift());

    private void printMenu() {
        System.out.printf("Current cipher: %s \n", recentStrategy.getName());
        System.out.println("Choose an option");
        System.out.println("A - Use Caesar Cipher");
        System.out.println("B - Use Keyed Caesar Cipher");
        System.out.println("C - Vigenere Cipher");
        System.out.println("D - Edit Key");
        System.out.println("E - Display Key");
        System.out.println("F - Input plain text file");
        System.out.println("G - Input cipher text file");
        System.out.println("H - Encrypt");
        System.out.println("I - Display cipher text");
        System.out.println("J - Save cipher text");
        System.out.println("K - Decrypt");
        System.out.println("L - Display plain text");
        System.out.println("M - Save plain text");
        System.out.println("Q - Quit the program");
    }

    private void runMenu() throws IOException {
        String character;
        do {
            System.out.println();
            printMenu();
            System.out.println("What would you like to do? ");
            scan = new Scanner(System.in);
            character = scan.nextLine().toUpperCase();
            switch (character) {
                case "A":
                    setStrategy(new CaesarShift());
                    break;
                case "B":
                    setStrategy(new KeyedCaesar());
                    break;
                case "C":
                    setStrategy(new VigenereCipher());
                    break;
                case "D":
                    editKey();
                    break;
                case "E":
                    System.out.println("Key is : " + recentKey);
                    break;
                case "F":
                    inputText(TextType.PLAIN_TEXT);
                    break;
                case "G":
                    inputText(TextType.CIPHER_TEXT);
                    break;
                case "H":
                    encryption();
                    break;
                case "I":
                    cipherTextChecking(recentCipherText);
                    break;
                case "J":
                    saveText(recentCipherText);
                    break;
                case "K":
                    decryption();
                    break;
                case "L":
                    plaintextChecking(recentPlainText);
                    break;
                case "M":
                    saveText(recentPlainText);
                    break;
                case "Q":
                    System.out.println("Thank you for using cipher machine");
                    break;
                default:
            }
        } while (!(character.equals("Q")));
    }

    private void setStrategy(Cipher cipher) {
        recentStrategy = new CipherStrategy(cipher);
        recentKey = null;
    }

    private void editKey() {
        String temp;
        scan = new Scanner(System.in);
        String INVALID_VALUE = "Invalid value";
        if (recentStrategy.getCipher() instanceof CaesarShift) {
            System.out.println("Enter key value for caesar shift: ");
            temp = scan.next();
            if (Pattern.compile("[\\d]+").matcher(temp).matches()) {
                recentKey = temp;
            } else
                System.err.println(INVALID_VALUE);
        } else if (recentStrategy.getCipher() instanceof KeyedCaesar) {
            System.out.println("Enter Key value and keyword for keyed caesar cipher");
            temp = scan.next();
            if (Pattern.compile("[0-9]+[a-zA-Z]+").matcher(temp).matches()) {
                recentKey = temp;
            } else {
                System.err.println(INVALID_VALUE);
            }
        } else if (recentStrategy.getCipher() instanceof VigenereCipher) {
            System.out.println("Enter keyword for Vigenere Cipher");
            temp = scan.next();
            if (Pattern.compile("[a-zA-Z]+").matcher(temp).matches()) {
                recentKey = temp;
            } else {
                System.err.println(INVALID_VALUE);
            }
        }
    }

    private void inputText(TextType textType) {
        System.out.println("Enter file name with text: ");
        try {
            filename = scan.next();
            if (textType.equals(TextType.PLAIN_TEXT)) {
                recentPlainText = load(filename);
            } else {
                recentCipherText = load(filename);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found. Try again.");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    private void encryption() {
        if (recentKey != null && recentPlainText != null) {
            recentCipherText = recentStrategy.cipher(recentPlainText, recentKey);
        } else {
            System.err.println("Add missing part (key or text)!");
        }
    }

    private void cipherTextChecking(String recentCipherText) {
        if (recentCipherText != null) {
            System.out.println("Cipher text is: " + recentCipherText.toUpperCase());
        } else {
            System.err.println("Provide the cipher text first!");
        }
    }

    private void saveText(String text) throws IOException {
        if (text != null) {
            System.out.println("Enter file name: ");
            String outfileName = scan.next();
            save(outfileName, text);
        } else System.err.println("Nothing to save");
    }


    private void decryption() {
        if (recentKey != null && recentCipherText != null) {
            recentPlainText = recentStrategy.decipher(recentCipherText, recentKey);
        } else {
            System.err.println("Edit the key first!");
        }
    }

    private void plaintextChecking(String recentPlainText) {
        if (recentPlainText != null) {
            System.out.println("Plain text is: " + recentPlainText.toUpperCase());
        } else {
            System.err.println("Provide the plain text first!");
        }
    }

    private String load(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            Scanner infile = new Scanner(br);
            return infile.nextLine().toUpperCase().replaceAll("(\\s)+", "");
    }

    private void save(String outfileName, String text) throws IOException {
        PrintWriter outfile = new PrintWriter(outfileName);
        outfile.println(text.toUpperCase());
        outfile.close();
    }


    public static void main(String[] args) throws IOException {
        CipherMenu app = new CipherMenu();
        app.runMenu();
    }


}

