import java.io.*;
import java.util.*;

public class MatrixExponentiation {

    static final int MOD = (int) 1e9 + 7;

    static long mul(long a, long b) {
        return a * b % MOD;
    }

    static long add(long a, long b) {
        return (a + b) % MOD;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        long n = sc.nextLong();

        Matrix transition = new Matrix(2);
        transition.mat[0][0] = 1;
        transition.mat[0][1] = 1;
        transition.mat[1][0] = 1;
        transition.mat[1][1] = 0;

        Matrix base = new Matrix(2, 1);
        base.mat[0][0] = 1;
        base.mat[1][0] = 0;

        transition = Matrix.pow(transition, n);

        base = Matrix.multiply(transition, base);

        pw.println(base.mat[1][0]);

        pw.flush();
        pw.close();
    }

    static class Matrix {
        long mat[][];

        Matrix(int n) {
            this(n, n);
        }

        Matrix(int n, int m) {
            mat = new long[n][m];
        }

        static Matrix Identity(int n) {
            Matrix res = new Matrix(n);
            for (int i = 0; i < n; i++) {
                res.mat[i][i] = 1;
            }
            return res;
        }

        static Matrix multiply(Matrix A, Matrix B) { // O(N^3)
            int aRows = A.mat.length;
            int aCols = A.mat[0].length;
            int bRows = B.mat.length;
            int bCols = B.mat[0].length;

            if (aCols != bRows) {
                throw new IllegalArgumentException("Matrices cannot be multiplied");
            }

            Matrix res = new Matrix(aRows, bCols);
            for (int i = 0; i < aRows; i++) {
                for (int j = 0; j < bCols; j++) {
                    // res[i][j] = A_row[i] . B_col[j]
                    for (int k = 0; k < aCols; k++) {
                        res.mat[i][j] = add(res.mat[i][j], mul(A.mat[i][k], B.mat[k][j]));
                    }
                }
            }
            return res;
        }

        static Matrix pow(Matrix M, long k) { // O(N^3 * log(k))
            Matrix res = Identity(M.mat.length);
            while (k > 0) {
                if (k % 2 == 1)
                    res = multiply(res, M);
                M = multiply(M, M);
                k >>= 1;
            }
            return res;
        }
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(String r) throws Exception {
            br = new BufferedReader(new FileReader(new File(r)));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public Long[] nextLongArray(int n) throws IOException {
            Long[] a = new Long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}