/**
 * August Cipher Implementation
 * A variant of the Vigenere cipher that uses a keyword for shifting.
 */
public class AugustCipher {
    
    /**
     * Encrypts or decrypts text using the August cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyword The keyword used for shifting
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String keyword, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        String cleanKeyword = keyword.toUpperCase().replaceAll("[^A-Z]", "");
        
        int keyLength = cleanKeyword.length();
        
        for (int i = 0; i < cleanText.length(); i++) {
            char textChar = cleanText.charAt(i);
            char keyChar = cleanKeyword.charAt(i % keyLength);
            
            int textValue = textChar - 'A';
            int keyValue = keyChar - 'A';
            
            int resultValue;
            if (encrypt) {
                resultValue = (textValue + keyValue) % 26;
            } else {
                resultValue = (textValue - keyValue + 26) % 26;
            }
            
            result.append((char) (resultValue + 'A'));
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