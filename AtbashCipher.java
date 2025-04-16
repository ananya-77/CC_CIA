/**
 * Atbash Cipher Implementation
 * This cipher replaces each letter with its mirror in the alphabet.
 */
public class AtbashCipher {
    
    /**
     * Encrypts or decrypts text using the Atbash cipher.
     * (Atbash is its own inverse, so encryption and decryption are the same)
     * 
     * @param text The text to encrypt/decrypt
     * @return The encrypted/decrypted text
     */
    public static String process(String text) {
        StringBuilder result = new StringBuilder();
        
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                // Mirror the character in the alphabet (A↔Z, B↔Y, etc.)
                result.append((char) (base + 25 - (character - base)));
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "HELLO";
        
        String encrypted = process(plaintext);
        String decrypted = process(encrypted);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}