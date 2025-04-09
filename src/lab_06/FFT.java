import java.io.*;
import java.util.*;

public class FFT {

    static final double PI = Math.PI;

    public static void fft(CD[] a, boolean invert) {
        int n = a.length;
        if (n == 1) {
            return;
        }
        CD[] a0 = new CD[n / 2], a1 = new CD[n / 2];
        for (int i = 0; i < n / 2; i++) {
            a0[i] = a[2 * i];
            a1[i] = a[2 * i + 1];
        }

        fft(a0, invert);
        fft(a1, invert);

        double ang = 2 * PI / n * (invert ? -1 : 1);
        CD w = new CD(1);
        CD wn = new CD(Math.cos(ang), Math.sin(ang));

        for (int i = 0; i < n / 2; i++) {
            a[i] = a0[i].add(w.multiply(a1[i]));
            a[i + n / 2] = a0[i].subtract(w.multiply(a1[i]));

            if (invert) {
                a[i] = a[i].divideByReal(2);
                a[i + n / 2] = a[i + n / 2].divideByReal(2);
            }

            w = w.multiply(wn);
        }
    }

    private static CD[] initializeComplexArray(long[] arr, int n) {
        CD[] complexArray = new CD[n];
        for (int i = 0; i < n; i++) {
            if (i < arr.length) {
                complexArray[i] = new CD(arr[i]);
            } else {
                complexArray[i] = new CD(0);
            }
        }
        return complexArray;
    }

    public static long[] multiply(long[] a, long[] b) {
        int n = 1;
        while (n < a.length + b.length) {
            n <<= 1;
        }

        CD[] aComplex = initializeComplexArray(a, n);
        CD[] bComplex = initializeComplexArray(b, n);
        fft(aComplex, false);
        fft(bComplex, false);

        CD[] cComplex = new CD[n];
        for (int i = 0; i < n; i++) {
            cComplex[i] = aComplex[i].multiply(bComplex[i]);
        }
        fft(cComplex, true);
        long[] c = new long[n];
        for (int i = 0; i < n; i++) {
            c[i] = Math.round(cComplex[i].real);
        }
        return c;
    }

    public static void main(String[] args) {
        
    }

    static class CD {
        double real;
        double imag;

        public CD(double real) {
            this.real = real;
            this.imag = 0;
        }

        public CD(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public CD add(CD other) {
            return new CD(this.real + other.real, this.imag + other.imag);
        }

        public CD subtract(CD other) {
            return new CD(this.real - other.real, this.imag - other.imag);
        }

        public CD multiply(CD other) {
            return new CD(
                    this.real * other.real - this.imag * other.imag,
                    this.real * other.imag + this.imag * other.real);
        }

        public CD divideByReal(double real) {
            return new CD(this.real / real, this.imag / real);
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