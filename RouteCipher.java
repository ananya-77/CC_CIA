/**
 * Route Cipher Implementation
 * This cipher arranges the plaintext in a grid and reads it off using a specific pattern.
 */
public class RouteCipher {
    
    /**
     * Encrypts or decrypts text using the Route cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param rows Number of rows in the grid
     * @param cols Number of columns in the grid
     * @param pattern The reading pattern (e.g., "spiral", "snake", "diagonal")
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, int rows, int cols, String pattern, boolean encrypt) {
        String cleanText = text.replaceAll("\\s", "");
        
        if (encrypt) {
            return encrypt(cleanText, rows, cols, pattern);
        } else {
            return decrypt(cleanText, rows, cols, pattern);
        }
    }
    
    private static String encrypt(String text, int rows, int cols, String pattern) {
        // Pad the text if needed
        StringBuilder paddedText = new StringBuilder(text);
        while (paddedText.length() < rows * cols) {
            paddedText.append('X');
        }
        text = paddedText.toString();
        
        // Fill the grid
        char[][] grid = new char[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = text.charAt(index++);
            }
        }
        
        // Read off the grid according to the pattern
        StringBuilder result = new StringBuilder();
        
        switch (pattern.toLowerCase()) {
            case "spiral":
                // Spiral pattern (clockwise from outside)
                int top = 0, bottom = rows - 1;
                int left = 0, right = cols - 1;
                
                while (top <= bottom && left <= right) {
                    // Move right
                    for (int i = left; i <= right; i++) {
                        result.append(grid[top][i]);
                    }
                    top++;
                    
                    // Move down
                    for (int i = top; i <= bottom; i++) {
                        result.append(grid[i][right]);
                    }
                    right--;
                    
                    // Move left
                    if (top <= bottom) {
                        for (int i = right; i >= left; i--) {
                            result.append(grid[bottom][i]);
                        }
                        bottom--;
                    }
                    
                    // Move up
                    if (left <= right) {
                        for (int i = bottom; i >= top; i--) {
                            result.append(grid[i][left]);
                        }
                        left++;
                    }
                }
                break;
                
            case "snake":
                // Snake pattern (alternating left-to-right and right-to-left)
                for (int i = 0; i < rows; i++) {
                    if (i % 2 == 0) {
                        // Left to right
                        for (int j = 0; j < cols; j++) {
                            result.append(grid[i][j]);
                        }
                    } else {
                        // Right to left
                        for (int j = cols - 1; j >= 0; j--) {
                            result.append(grid[i][j]);
                        }
                    }
                }
                break;
                
            case "diagonal":
                // Diagonal pattern
                for (int sum = 0; sum <= rows + cols - 2; sum++) {
                    for (int i = 0; i <= sum; i++) {
                        int j = sum - i;
                        if (i < rows && j < cols) {
                            result.append(grid[i][j]);
                        }
                    }
                }
                break;
                
            default:
                // Default to row-by-row
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        result.append(grid[i][j]);
                    }
                }
        }
        
        return result.toString();
    }
    
    private static String decrypt(String text, int rows, int cols, String pattern) {
        // Create a grid to hold the decrypted text
        char[][] grid = new char[rows][cols];
        
        // Initialize the grid with placeholder characters
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = ' ';
            }
        }
        
        // Fill the grid according to the pattern
        int index = 0;
        
        switch (pattern.toLowerCase()) {
            case "spiral":
                // Spiral pattern (clockwise from outside)
                int top = 0, bottom = rows - 1;
                int left = 0, right = cols - 1;
                
                while (top <= bottom && left <= right && index < text.length()) {
                    // Move right
                    for (int i = left; i <= right && index < text.length(); i++) {
                        grid[top][i] = text.charAt(index++);
                    }
                    top++;
                    
                    // Move down
                    for (int i = top; i <= bottom && index < text.length(); i++) {
                        grid[i][right] = text.charAt(index++);
                    }
                    right--;
                    
                    // Move left
                    if (top <= bottom) {
                        for (int i = right; i >= left && index < text.length(); i--) {
                            grid[bottom][i] = text.charAt(index++);
                        }
                        bottom--;
                    }
                    
                    // Move up
                    if (left <= right) {
                        for (int i = bottom; i >= top && index < text.length(); i--) {
                            grid[i][left] = text.charAt(index++);
                        }
                        left++;
                    }
                }
                break;
                
            case "snake":
                // Snake pattern (alternating left-to-right and right-to-left)
                for (int i = 0; i < rows && index < text.length(); i++) {
                    if (i % 2 == 0) {
                        // Left to right
                        for (int j = 0; j < cols && index < text.length(); j++) {
                            grid[i][j] = text.charAt(index++);
                        }
                    } else {
                        // Right to left
                        for (int j = cols - 1; j >= 0 && index < text.length(); j--) {
                            grid[i][j] = text.charAt(index++);
                        }
                    }
                }
                break;
                
            case "diagonal":
                // Diagonal pattern
                for (int sum = 0; sum <= rows + cols - 2 && index < text.length(); sum++) {
                    for (int i = 0; i <= sum && index < text.length(); i++) {
                        int j = sum - i;
                        if (i < rows && j < cols) {
                            grid[i][j] = text.charAt(index++);
                        }
                    }
                }
                break;
                
            default:
                // Default to row-by-row
                for (int i = 0; i < rows && index < text.length(); i++) {
                    for (int j = 0; j < cols && index < text.length(); j++) {
                        grid[i][j] = text.charAt(index++);
                    }
                }
        }
        
        // Read the grid row by row to get the plaintext
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.append(grid[i][j]);
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        String plaintext = "WEAREDISCOVEREDRUNATONCE";
        int rows = 4;
        int cols = 6;
        String pattern = "spiral";
        
        String encrypted = process(plaintext, rows, cols, pattern, true);
        String decrypted = process(encrypted, rows, cols, pattern, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}