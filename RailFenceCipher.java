/**
 * Rail Fence Cipher Implementation
 * This is a transposition cipher that arranges the plaintext in a zigzag pattern.
 */
public class RailFenceCipher {
    
    /**
     * Encrypts or decrypts text using the Rail Fence cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param rails The number of rails (rows)
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, int rails, boolean encrypt) {
        if (rails < 2) {
            throw new IllegalArgumentException("Number of rails must be at least 2");
        }
        
        if (encrypt) {
            return encrypt(text, rails);
        } else {
            return decrypt(text, rails);
        }
    }
    
    /**
     * Encrypts text using the Rail Fence cipher.
     */
    private static String encrypt(String text, int rails) {
        // Remove spaces
        String cleanText = text.replaceAll("\\s", "");
        
        // Create the rail fence
        char[][] fence = new char[rails][cleanText.length()];
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < cleanText.length(); j++) {
                fence[i][j] = '\0';
            }
        }
        
        // Fill the fence with characters
        int row = 0;
        int direction = 1;  // 1 = down, -1 = up
        
        for (int i = 0; i < cleanText.length(); i++) {
            fence[row][i] = cleanText.charAt(i);
            
            // Change direction if we hit the top or bottom rail
            if (row == 0) {
                direction = 1;
            } else if (row == rails - 1) {
                direction = -1;
            }
            
            row += direction;
        }
        
        // Read off the fence
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < cleanText.length(); j++) {
                if (fence[i][j] != '\0') {
                    result.append(fence[i][j]);
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Decrypts text using the Rail Fence cipher.
     */
    private static String decrypt(String text, int rails) {
        char[][] fence = new char[rails][text.length()];
        
        // Initialize the fence with placeholder characters
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < text.length(); j++) {
                fence[i][j] = '\0';
            }
        }
        
        // Mark the positions where characters will be placed
        int row = 0;
        int direction = 1;
        for (int i = 0; i < text.length(); i++) {
            fence[row][i] = '*';
            
            if (row == 0) {
                direction = 1;
            } else if (row == rails - 1) {
                direction = -1;
            }
            
            row += direction;
        }
        
        // Fill the fence with characters from the ciphertext
        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (fence[i][j] == '*' && index < text.length()) {
                    fence[i][j] = text.charAt(index++);
                }
            }
        }
        
        // Read off the fence in zigzag pattern
        StringBuilder result = new StringBuilder();
        row = 0;
        direction = 1;
        for (int i = 0; i < text.length(); i++) {
            result.append(fence[row][i]);
            
            if (row == 0) {
                direction = 1;
            } else if (row == rails - 1) {
                direction = -1;
            }
            
            row += direction;
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "DEFENDTHEEASTWALL";
        int rails = 3;
        
        String encrypted = process(plaintext, rails, true);
        String decrypted = process(encrypted, rails, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}