import java.util.*;

import java.io.*;

public class SegmentTree {

    public static void main(String[] args) throws IOException {
        int n = sc.nextInt();
        int N = 1;
        while (N < n) {
            N <<= 1;
        }
        int[] arr = new int[N + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
        }

        Node root = Node.construct(arr, 1, N);

        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                int index = sc.nextInt(), value = sc.nextInt();
                Node.update(root, index, value);
            } else {
                int l = sc.nextInt(), r = sc.nextInt();
                pw.println(Node.query(root, l, r));
            }
        }

        pw.flush();
    }

    static class Node {
        Node left, right;
        int sum, l, r, lazy;

        Node() {
            lazy = -1;
        }

        Node(int l, int r, int sum) {
            this.l = l;
            this.r = r;
            this.sum = sum;
            lazy = -1;
        }

        void merge(Node leftCh, Node rightCh) {
            this.sum = leftCh.sum + rightCh.sum;
        }

        void set(int value) {
            this.sum = value;
        }

        public static Node construct(int[] arr, int L, int R) {
            if (L == R) {
                return new Node(L, R, arr[L]);
            } else {
                Node result = new Node();
                int mid = (L + R) / 2;
                Node left = construct(arr, L, mid), right = construct(arr, mid + 1, R);
                result.left = left;
                result.right = right;
                result.l = left.l;
                result.r = right.r;
                result.merge(result.left, result.right);
                return result;
            }
        }

        public static void update(Node curr, int index, int value) {
            if (curr.l == curr.r) {
                curr.set(value);
            } else {
                int mid = (curr.l + curr.r) / 2;
                if (index > mid) {
                    update(curr.right, index, value);
                } else {
                    update(curr.left, index, value);
                }
                curr.merge(curr.left, curr.right);
            }
        }

        public static int query(Node curr, int L, int R) {
            if (curr.l >= L && curr.r <= R) {
                return curr.sum;
            }
            int mid = (curr.l + curr.r) / 2;
            int sum = 0;
            curr.propagate(curr.lazy);
            if (L <= mid) {
                sum += query(curr.left, L, R);
            }
            if (R > mid) {
                sum += query(curr.right, L, R);
            }
            return sum;
        }

        public static void updateRange(Node curr, int L, int R, int value) {
            if (curr.l >= L && curr.r <= R) {
                curr.sum = (curr.r - curr.l + 1) * value;
                curr.lazy = value;
                return;
            }
            int mid = (curr.l + curr.r) / 2;
            curr.propagate(curr.lazy);
            if (L <= mid) {
                updateRange(curr.left, L, R, value);
            }
            if (R > mid) {
                updateRange(curr.right, L, R, value);
            }
            curr.merge(curr.left, curr.right);
        }

        void propagate(int value) {
            if (value == -1)
                return;
            this.left.sum = (this.left.r - this.left.l + 1) * value;
            this.right.sum = (this.right.r - this.right.l + 1) * value;
            this.left.lazy = value;
            this.right.lazy = value;
            this.lazy = -1;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);


    static class Scanner {

        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(String file) throws IOException {
            br = new BufferedReader(new FileReader(file));
        }

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String readAllLines(BufferedReader reader) throws IOException {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
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

        public long[] nextlongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
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
