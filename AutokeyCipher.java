/**
 * Autokey Cipher Implementation (also known as Running Key Cipher)
 * This cipher uses the plaintext itself as part of the key after an initial primer key.
 */
public class AutokeyCipher {
    
    /**
     * Encrypts or decrypts text using the Autokey cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param primer The initial key value
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String primer, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        String cleanPrimer = primer.toUpperCase().replaceAll("[^A-Z]", "");
        
        if (cleanPrimer.isEmpty()) {
            throw new IllegalArgumentException("Primer cannot be empty");
        }
        
        // Generate the full key using the primer and plaintext
        StringBuilder keyStream = new StringBuilder(cleanPrimer);
        
        if (encrypt) {
            // For encryption, append plaintext to the primer
            for (int i = 0; i < cleanText.length() - cleanPrimer.length(); i++) {
                keyStream.append(cleanText.charAt(i));
            }
        }
        
        for (int i = 0; i < cleanText.length(); i++) {
            char textChar = cleanText.charAt(i);
            char keyChar;
            
            if (encrypt || i < cleanPrimer.length()) {
                keyChar = keyStream.charAt(i);
            } else {
                // For decryption beyond the primer length, use previously decrypted characters
                keyChar = result.charAt(i - cleanPrimer.length());
            }
            
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
        String primer = "KEY";
        
        String encrypted = process(plaintext, primer, true);
        String decrypted = process(encrypted, primer, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}