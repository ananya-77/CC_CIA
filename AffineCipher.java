/**
 * Affine Cipher Implementation
 * This cipher is a type of monoalphabetic substitution cipher that uses the function (ax + b) mod m
 * where x is the character position, a and b are keys, and m is the size of the alphabet.
 */
public class AffineCipher {
    
    /**
     * Encrypts or decrypts text using the Affine cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param a The multiplicative key (must be coprime with 26)
     * @param b The additive key
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, int a, int b, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        
        // Check if a is coprime with 26
        if (gcd(a, 26) != 1) {
            throw new IllegalArgumentException("The value of 'a' must be coprime with 26");
        }
        
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int x = character - base;
                
                int resultValue;
                if (encrypt) {
                    // E(x) = (ax + b) mod 26
                    resultValue = (a * x + b) % 26;
                } else {
                    // D(y) = a^-1 * (y - b) mod 26
                    int aInverse = multiplicativeInverse(a, 26);
                    resultValue = (aInverse * (x - b + 26)) % 26;
                }
                
                result.append((char) (resultValue + base));
            } else {
                result.append(character);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Calculates the greatest common divisor of two numbers.
     */
    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    
    /**
     * Calculates the multiplicative inverse of a number modulo m.
     */
    private static int multiplicativeInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }
    
    public static void main(String[] args) {
        String plaintext = "HELLO";
        int a = 5;  // Must be coprime with 26
        int b = 8;
        
        String encrypted = process(plaintext, a, b, true);
        String decrypted = process(encrypted, a, b, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}