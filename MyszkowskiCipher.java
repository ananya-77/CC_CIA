/**
 * Myszkowski Transposition Cipher Implementation
 * This is a transposition cipher that uses a keyword to determine column order.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyszkowskiCipher {
    
    /**
     * Encrypts or decrypts text using the Myszkowski Transposition cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyword The keyword that determines the transposition order
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, String keyword, boolean encrypt) {
        if (encrypt) {
            return encrypt(text, keyword);
        } else {
            return decrypt(text, keyword);
        }
    }
    
    private static String encrypt(String text, String keyword) {
        // Remove spaces
        String cleanText = text.replaceAll("\\s", "");
        
        // Create the unique key by assigning numbers to the keyword letters
        int[] keyNumbers = getKeyNumbers(keyword);
        
        // Calculate number of rows needed
        int keyLength = keyword.length();
        int textLength = cleanText.length();
        int rows = (int) Math.ceil((double) textLength / keyLength);
        
        // Create the grid
        char[][] grid = new char[rows][keyLength];
        
        // Fill the grid with the plaintext
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (index < textLength) {
                    grid[i][j] = cleanText.charAt(index++);
                } else {
                    grid[i][j] = 'X'; // Padding
                }
            }
        }
        
        // Read off the grid according to the key order
        StringBuilder result = new StringBuilder();
        
        // Group columns with the same key number
        Map<Integer, List<Integer>> keyGroups = new TreeMap<>();
        for (int i = 0; i < keyNumbers.length; i++) {
            if (!keyGroups.containsKey(keyNumbers[i])) {
                keyGroups.put(keyNumbers[i], new ArrayList<>());
            }
            keyGroups.get(keyNumbers[i]).add(i);
        }
        
        // Read columns in key order
        for (Map.Entry<Integer, List<Integer>> entry : keyGroups.entrySet()) {
            List<Integer> columnIndices = entry.getValue();
            
            // Read grouped columns from top to bottom
            for (int i = 0; i < rows; i++) {
                for (int colIndex : columnIndices) {
                    if (i < rows && colIndex < keyLength) {
                        result.append(grid[i][colIndex]);
                    }
                }
            }
        }
        
        return result.toString();
    }
    
    private static String decrypt(String text, String keyword) {
        int[] keyNumbers = getKeyNumbers(keyword);
        int keyLength = keyword.length();
        int textLength = text.length();
        int rows = (int) Math.ceil((double) textLength / keyLength);
        
        // Group columns with the same key number
        Map<Integer, List<Integer>> keyGroups = new TreeMap<>();
        for (int i = 0; i < keyNumbers.length; i++) {
            if (!keyGroups.containsKey(keyNumbers[i])) {
                keyGroups.put(keyNumbers[i], new ArrayList<>());
            }
            keyGroups.get(keyNumbers[i]).add(i);
        }
        
        // Calculate the number of characters in each column group
        Map<Integer, Integer> groupSizes = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : keyGroups.entrySet()) {
            groupSizes.put(entry.getKey(), entry.getValue().size() * rows);
        }
        
        // Adjust for any incomplete final row
        int lastGroupKey = -1;
        for (int key : keyGroups.keySet()) {
            if (key > lastGroupKey) {
                lastGroupKey = key;
            }
        }
        
        int totalSize = 0;
        for (int size : groupSizes.values()) {
            totalSize += size;
        }
        
        int overflow = totalSize - textLength;
        if (overflow > 0) {
            List<Integer> lastGroup = keyGroups.get(lastGroupKey);
            groupSizes.put(lastGroupKey, groupSizes.get(lastGroupKey) - overflow);
        }
        
        // Create a grid to hold the decrypted text
        char[][] grid = new char[rows][keyLength];
        
        // Fill the grid using the ciphertext
        int index = 0;
        for (Map.Entry<Integer, List<Integer>> entry : keyGroups.entrySet()) {
            int key = entry.getKey();
            List<Integer> columnIndices = entry.getValue();
            int groupSize = groupSizes.get(key);
            String groupText = text.substring(index, index + groupSize);
            index += groupSize;
            
            int groupIndex = 0;
            for (int i = 0; i < rows; i++) {
                for (int colIndex : columnIndices) {
                    if (groupIndex < groupText.length()) {
                        grid[i][colIndex] = groupText.charAt(groupIndex++);
                    }
                }
            }
        }
        
        // Read the grid row by row to get the plaintext
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (grid[i][j] != '\0') {
                    result.append(grid[i][j]);
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Assigns numbers to the keyword letters based on their alphabetical order.
     * Letters with the same value get the same number.
     */
    private static int[] getKeyNumbers(String keyword) {
        int length = keyword.length();
        int[] keyNumbers = new int[length];
        
        // Convert keyword to uppercase for consistent comparison
        String upperKeyword = keyword.toUpperCase();
        
        // Find unique characters and sort them
        char[] sortedChars = upperKeyword.toCharArray();
        java.util.Arrays.sort(sortedChars);
        
        // Remove duplicates
        StringBuilder uniqueChars = new StringBuilder();
        char lastChar = '\0';
        for (char c : sortedChars) {
            if (c != lastChar) {
                uniqueChars.append(c);
                lastChar = c;
            }
        }
        
        // Assign numbers based on alphabetical order
        for (int i = 0; i < length; i++) {
            char c = upperKeyword.charAt(i);
            keyNumbers[i] = uniqueChars.toString().indexOf(c) + 1;
        }
        
        return keyNumbers;
    }
    
    public static void main(String[] args) {
        String plaintext = "WEAREDISCOVEREDRUNATONCE";
        String keyword = "KEYWORD";
        
        String encrypted = process(plaintext, keyword, true);
        String decrypted = process(encrypted, keyword, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}