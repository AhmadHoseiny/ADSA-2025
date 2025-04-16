package Lab_07;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class GraphAlgorthims {
    static ArrayList<Integer> graph[];
    static int[] vis, caller, parent;
    static boolean foundCycle = false;
    static void findCycleOnDG(int node) {
        if(foundCycle)
            return;
        vis[node] = 1;
        for (int next : graph[node]) {
            if (vis[next] == 1) {
                int currentNode = node;
                while (true) {
                    System.out.print(currentNode + 1 + " ");
                    if(currentNode == next)
                        break;
                    currentNode = caller[currentNode];
                }
                foundCycle = true;
                return;
            } else if (vis[next] == 0) {
                caller[next] = node;
                findCycleOnDG(next);
            }
        }
        vis[node] = 2;
    }


    static void findCycleOnUDG(int node, int parent) {
        if(foundCycle)
            return;
        vis[node] = 1;
        for (int next : graph[node]) {
            if(next == parent)
                continue;
            if(foundCycle)
                return;
            if (vis[next] == 1) {
                int currentNode = node;
                while (true) {
                    System.out.print(currentNode + 1 + " ");
                    if(currentNode == next)
                        break;
                    currentNode = caller[currentNode];
                }
                foundCycle = true;
                return;
            } else if (vis[next] == 0) {
                caller[next] = node;
                findCycleOnUDG(next, node);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        vis = new int[n];
        caller = new int[n];
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                findCycleOnUDG(i, -1);
            }
        }


    }

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
