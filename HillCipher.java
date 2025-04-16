/**
 * Hill Cipher Implementation
 * This cipher uses matrix multiplication for encryption and decryption.
 */
public class HillCipher {
    
    /**
     * Encrypts or decrypts text using the Hill cipher.
     * 
     * @param text The text to encrypt/decrypt
     * @param keyMatrix The key matrix (must be square)
     * @param encrypt True for encryption, false for decryption
     * @return The encrypted/decrypted text
     */
    public static String process(String text, int[][] keyMatrix, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        
        // Get the size of the matrix
        int n = keyMatrix.length;
        
        // Check if the matrix is square
        for (int[] row : keyMatrix) {
            if (row.length != n) {
                throw new IllegalArgumentException("Key matrix must be square");
            }
        }
        
        // If decrypting, find the inverse matrix
        int[][] workingMatrix = keyMatrix;
        if (!encrypt) {
            workingMatrix = inverseMatrix(keyMatrix);
        }
        
        // Pad the text if needed
        if (cleanText.length() % n != 0) {
            int paddingLength = n - (cleanText.length() % n);
            StringBuilder paddedText = new StringBuilder(cleanText);
            for (int i = 0; i < paddingLength; i++) {
                paddedText.append('X');
            }
            cleanText = paddedText.toString();
        }
        
        // Process the text in blocks of size n
        for (int i = 0; i < cleanText.length(); i += n) {
            int[] vector = new int[n];
            
            // Convert the block to a vector
            for (int j = 0; j < n; j++) {
                vector[j] = cleanText.charAt(i + j) - 'A';
            }
            
            // Multiply the vector by the key matrix
            int[] resultVector = new int[n];
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    resultVector[row] = (resultVector[row] + workingMatrix[row][col] * vector[col]) % 26;
                }
                resultVector[row] = (resultVector[row] + 26) % 26;  // Ensure positive value
            }
            
            // Convert the result vector back to text
            for (int j = 0; j < n; j++) {
                result.append((char) (resultVector[j] + 'A'));
            }
        }
        
        return result.toString();
    }
    
    /**
     * Calculates the determinant of a matrix.
     */
    private static int determinant(int[][] matrix) {
        int n = matrix.length;
        
        if (n == 1) {
            return matrix[0][0];
        }
        
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        }
        
        int det = 0;
        for (int i = 0; i < n; i++) {
            int[][] subMatrix = new int[n-1][n-1];
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (k < i) {
                        subMatrix[j-1][k] = matrix[j][k];
                    } else if (k > i) {
                        subMatrix[j-1][k-1] = matrix[j][k];
                    }
                }
            }
            det = (det + (int)Math.pow(-1, i) * matrix[0][i] * determinant(subMatrix)) % 26;
        }
        
        return (det + 26) % 26;  // Ensure positive value
    }
    
    /**
     * Calculates the adjugate matrix.
     */
    private static int[][] adjugate(int[][] matrix) {
        int n = matrix.length;
        int[][] adj = new int[n][n];
        
        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[][] subMatrix = new int[n-1][n-1];
                int row = 0;
                for (int k = 0; k < n; k++) {
                    if (k != i) {
                        int col = 0;
                        for (int l = 0; l < n; l++) {
                            if (l != j) {
                                subMatrix[row][col] = matrix[k][l];
                                col++;
                            }
                        }
                        row++;
                    }
                }
                adj[j][i] = ((int)Math.pow(-1, i+j) * determinant(subMatrix) + 26) % 26;
            }
        }
        
        return adj;
    }
    
    /**
     * Calculates the multiplicative inverse of a number modulo 26.
     */
    private static int modInverse(int a) {
        a = a % 26;
        for (int x = 1; x < 26; x++) {
            if ((a * x) % 26 == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Multiplicative inverse does not exist");
    }
    
    /**
     * Calculates the inverse of a matrix modulo 26.
     */
    private static int[][] inverseMatrix(int[][] matrix) {
        int n = matrix.length;
        int det = determinant(matrix);
        
        // Check if the determinant is invertible
        if (det == 0 || gcd(det, 26) != 1) {
            throw new IllegalArgumentException("Matrix is not invertible modulo 26");
        }
        
        int detInverse = modInverse(det);
        int[][] adj = adjugate(matrix);
        int[][] inverse = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (adj[i][j] * detInverse) % 26;
            }
        }
        
        return inverse;
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
    
    public static void main(String[] args) {
        String plaintext = "HELLO";
        int[][] keyMatrix = {
            {6, 24},
            {13, 16}
        };
        
        String encrypted = process(plaintext, keyMatrix, true);
        String decrypted = process(encrypted, keyMatrix, false);
        
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}