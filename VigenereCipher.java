/**
 * Vigenere Cipher Implementation
 * This cipher uses a keyword to perform multiple shifts on the plaintext.
 */
public class VigenereCipher {
    
    /**
     * Encrypts or decrypts text using the Vigenere cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyword The keyword used for shifting
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String keyword, boolean encrypt) {
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
                int shift = keyChar - 'A';
                
                int textValue = character - base;
                int resultValue;
                
                if (encrypt) {
                    resultValue = (textValue + shift) % 26;
                } else {
                    resultValue = (textValue - shift + 26) % 26;
                }
                
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
        
        String encrypted = process(plaintext, keyword, true);
        String decrypted = process(encrypted, keyword, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}