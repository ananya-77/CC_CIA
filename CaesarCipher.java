/**
 * Caesar Cipher Implementation
 * This cipher shifts each letter by a fixed number of positions in the alphabet.
 */
public class CaesarCipher {
    
    /**
     * Encrypts or decrypts text using the Caesar cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param shift The number of positions to shift (key)
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, int shift, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        
        // Adjust shift for decryption
        if (!encrypt) {
            shift = 26 - (shift % 26);
        }
        
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((((character - base) + shift) % 26) + base));
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "HELLO";
        int shift = 3;
        
        String encrypted = process(plaintext, shift, true);
        String decrypted = process(encrypted, shift, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}