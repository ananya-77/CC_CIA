/**
 * N-Gram Cipher Implementation
 * This cipher substitutes groups of n letters with other groups of n letters.
 */
import java.util.HashMap;
import java.util.Map;

public class NGramCipher {
    
    /**
     * Encrypts text using N-Gram substitution.
     * 
     * @param text The text to encrypt
     * @param substitutionMap Map of N-grams to their substitutions
     * @param n The size of the N-grams
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted text
     */
    public static String process(String text, Map<String, String> substitutionMap, int n, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        
        if (encrypt) {
            // For encryption
            for (int i = 0; i <= cleanText.length() - n; i += n) {
                String ngram = cleanText.substring(i, i + n);
                if (substitutionMap.containsKey(ngram)) {
                    result.append(substitutionMap.get(ngram));
                } else {
                    result.append(ngram);
                }
            }
            
            // Handle remaining characters
            if (cleanText.length() % n != 0) {
                result.append(cleanText.substring(cleanText.length() - (cleanText.length() % n)));
            }
        } else {
            // For decryption, we need to invert the map
            Map<String, String> invertedMap = new HashMap<>();
            for (Map.Entry<String, String> entry : substitutionMap.entrySet()) {
                invertedMap.put(entry.getValue(), entry.getKey());
            }
            
            // Now decrypt using the inverted map
            for (int i = 0; i <= cleanText.length() - n; i += n) {
                String ngram = cleanText.substring(i, i + n);
                if (invertedMap.containsKey(ngram)) {
                    result.append(invertedMap.get(ngram));
                } else {
                    result.append(ngram);
                }
            }
            
            // Handle remaining characters
            if (cleanText.length() % n != 0) {
                result.append(cleanText.substring(cleanText.length() - (cleanText.length() % n)));
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "HELLOWORLD";
        Map<String, String> bigramMap = new HashMap<>();
        bigramMap.put("HE", "ZY");
        bigramMap.put("LL", "XW");
        bigramMap.put("OW", "VU");
        bigramMap.put("OR", "TS");
        bigramMap.put("LD", "RQ");
        
        String encrypted = process(plaintext, bigramMap, 2, true);
        String decrypted = process(encrypted, bigramMap, 2, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}