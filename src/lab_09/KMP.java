import java.util.Scanner;

public class KMP {
    
    public static void computePrefixFunction(String pattern, int[] pi) {
        int m = pattern.length();
        pi[0] = 0;

        for (int q = 1; q < m; q++) {
            int k = pi[q - 1];
            while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
                k = pi[k - 1];
            }
            if (pattern.charAt(k) == pattern.charAt(q)) {
                k++;
            }
            pi[q] = k;
        }
    }

    public static void computePrefixFunctionN2(String pattern, int[] pi) {
        int m = pattern.length();
        pi[0] = 0;

        int j = 1;

        for (int i = 1; i < m; i++) {
            if (pattern.substring(0, j).equals(pattern.substring(i - j + 1, i + 1))) {
                pi[i] = j;
                j++;
            } else {
                j--;
                while (j > 0 && !pattern.substring(i - j + 1, i + 1).equals(pattern.substring(0, j))) {
                    j--;
                }
                if (j > 0) {
                    pi[i] = j;
                    j++;
                } else {
                    pi[i] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
    }

}
