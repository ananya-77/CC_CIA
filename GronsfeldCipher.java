/**
 * Gronsfeld Cipher Implementation
 * A variant of the Vigenere cipher that uses digits instead of letters as the key.
 */
public class GronsfeldCipher {
    
    /**
     * Encrypts or decrypts text using the Gronsfeld cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyDigits The key as a string of digits
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String keyDigits, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        
        if (keyDigits.isEmpty() || !keyDigits.matches("[0-9]+")) {
            throw new IllegalArgumentException("Key must be a non-empty string of digits");
        }
        
        int keyIndex = 0;
        
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int shift = Character.getNumericValue(keyDigits.charAt(keyIndex % keyDigits.length()));
                
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
        String keyDigits = "31415";
        
        String encrypted = process(plaintext, keyDigits, true);
        String decrypted = process(encrypted, keyDigits, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}