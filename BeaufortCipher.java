/**
 * Beaufort Cipher Implementation
 * This cipher is similar to Vigenere but uses a different formula for encryption.
 * The Beaufort cipher is its own inverse, meaning the same process works for both encryption and decryption.
 */
public class BeaufortCipher {
    
    /**
     * Processes text using the Beaufort cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyword The keyword used for encryption/decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String keyword) {
        StringBuilder result = new StringBuilder();
        String cleanKeyword = keyword.toUpperCase().replaceAll("[^A-Z]", "");
        
        if (cleanKeyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be empty");
        }
        
        int keyIndex = 0;
        
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                char keyChar = cleanKeyword.charAt(keyIndex % cleanKeyword.length());
                
                // Beaufort formula: E(x) = D(x) = (key - text) mod 26
                int resultValue = (keyChar - (character - base) + 26) % 26;
                
                result.append((char) (resultValue + base));
                keyIndex++;
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "HELLO";
        String keyword = "KEY";
        
        String encrypted = process(plaintext, keyword);
        String decrypted = process(encrypted, keyword);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}