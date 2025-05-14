import java.util.*;

public class MaxFlow {

    static final int INF = (int) 1e9;

    int n; // total number of nodes in the network including src and sink
    ArrayList<Integer>[] adj; // residual graph
    int[][] capacity;

    @SuppressWarnings("unchecked")
    public MaxFlow(int n) {
        this.n = n;
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        capacity = new int[n][n];
    }

    private int bfs(int s, int t, int[] par) {
        Arrays.fill(par, -1);
        par[s] = -2;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] { s, INF });
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int cur = p[0], flow = p[1];
            for (int nxt : adj[cur]) {
                if (par[nxt] == -1 && capacity[cur][nxt] > 0) {
                    par[nxt] = cur;
                    int newFlow = Math.min(flow, capacity[cur][nxt]);
                    if (nxt == t) {
                        return newFlow;
                    }
                    q.add(new int[] { nxt, newFlow });
                }
            }
        }
        return 0;
    }

    public int calc(int s, int t) {
        int flow = 0, newFlow;
        int[] par = new int[n];

        while (true) {
            newFlow = bfs(s, t, par);
            if (newFlow == 0)
                return flow;
            flow += newFlow;
            int cur = t;
            while (cur != s) {
                int prev = par[cur];
                capacity[prev][cur] -= newFlow;
                capacity[cur][prev] += newFlow;
                cur = prev;
            }
        }
    }

    public void addEdge(int u, int v, int cap) {
        adj[u].add(v);
        adj[v].add(u); // reverse edge for the residual graph
        capacity[u][v] += cap;
    }
}
