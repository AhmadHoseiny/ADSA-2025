package lab_07;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class GraphAlgorthims {
    static Scanner sc = new Scanner(System.in);

    static PrintWriter pw = new PrintWriter(System.out);
    static ArrayList<Integer> graph[], revGraph[], resGraph[];
    static int[] vis, caller, parent, compIdx;
    static boolean foundCycle = false;

    static ArrayList<Integer> order, currentComp;

    static void findCycleOnDG(int node) {
        if (foundCycle)
            return;
        vis[node] = 1;
        for (int next : graph[node]) {
            if (vis[next] == 1) {
                int currentNode = node;
                while (true) {
                    System.out.print(currentNode + 1 + " ");
                    if (currentNode == next)
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
        if (foundCycle)
            return;
        vis[node] = 1;
        for (int next : graph[node]) {
            if (next == parent)
                continue;
            if (foundCycle)
                return;
            if (vis[next] == 1) {
                int currentNode = node;
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(next);
                while (true) {
                    arr.add(currentNode);
                    if (currentNode == next)
                        break;
                    currentNode = caller[currentNode];
                }
                System.out.println(arr.size());
                for (Integer x : arr) {
                    System.out.print(x + 1 + " ");
                }
                foundCycle = true;
                System.exit(0);
                return;
            } else if (vis[next] == 0) {
                caller[next] = node;
                findCycleOnUDG(next, node);
            }
        }
    }


    static void dfs1(int node) {
        vis[node] = 1;
        for (int nxt : graph[node]) {
            if (vis[nxt] == 0) {
                dfs1(nxt);
            }
        }
        order.add(node);
    }

    static int curIdx;

    static void dfs2(int node) {
        vis[node] = 1;
        currentComp.add(node);
        for (int nxt : revGraph[node]) {
            if (vis[nxt] == 0) {
                dfs2(nxt);
            }
        }
        compIdx[node] = curIdx;
    }

    static void makeScc(int n, int m) throws IOException {
        revGraph = new ArrayList[n];
        resGraph = new ArrayList[n];
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            resGraph[i] = new ArrayList<>();
            revGraph[i] = new ArrayList<>();
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
            graph[u].add(v);
            revGraph[v].add(u);
        }
        vis = new int[n];
        compIdx = new int[n];
        order = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                dfs1(i);
            }
        }
        Collections.reverse(order);
        vis = new int[n];
        // the order array now is a stack, and the iterating over it = popping from the stack
        for (int i = 0; i < n; i++) {
            if (vis[order.get(i)] == 0) {
                currentComp = new ArrayList<>();
                curIdx = order.get(i);
                dfs2(order.get(i));
                // process the current component
            }
        }

        for (int i = 0; i < n; i++) {
            for (int nxt : graph[i]) {
                if (compIdx[i] != compIdx[nxt]) {
                    resGraph[compIdx[i]].add(compIdx[nxt]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        // remove one of them depending on the problem

        // scc
        makeScc(n, m);

        // cycle detection
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
        System.out.println("IMPOSSIBLE");
        pw.flush();
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
