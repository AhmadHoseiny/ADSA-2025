package lab_08;

import java.util.ArrayList;

public class LCA {
    int n, l, timer;
    ArrayList<Integer>[] adjL;
    int[] lvl, tin, tout;
    int[][] up;
    public LCA(int n, ArrayList<Integer>[] adjL){
        this.n = n;
        this.adjL = adjL;
        this.l = (int) Math.ceil(log2(n));
        this.timer = 0;
        this.lvl = new int[n];
        this.tin = new int[n];
        this.tout = new int[n];
        this.up = new int[n][l+1]; // up[i][j] is the (2^j)th ancestor of i
        dfs(0, -1, 0);
        for(int j=1 ; j<=l ; j++){
            for(int i=0 ; i<n ; i++){
                up[i][j] = (up[i][j-1]==-1)? -1 : up[up[i][j-1]][j-1];
            }
        }
    }
    private static double log2(int x){
        return Math.log(x)/Math.log(2);
    }
    private void dfs(int cur, int par, int level){
        lvl[cur] = level;
        up[cur][0] = par;
        tin[cur] = timer++;
        for(int nxt : adjL[cur]){
            if(nxt == par){
                continue;
            }
            dfs(nxt, cur, level+1);
        }
        tout[cur] = timer++;
    }

    public boolean isAncestor(int u, int v){
        return tin[u]<=tin[v] && tout[u]>=tout[v];
    }
    public int getLCA(int u, int v){
        if(isAncestor(u, v)){
            return u;
        }
        if(isAncestor(v, u)){
            return v;
        }
        for(int i=l ; i>=0 ; i--){
            if(up[u][i]!=-1 && !isAncestor(up[u][i], v)){
                u = up[u][i];
            }
        }
        return up[u][0];
    }

    // apps
    public int getDist(int u, int v){
        return lvl[u] + lvl[v] - 2*lvl[getLCA(u, v)];
    }
    public int climbKNodes(int u, int k){
        for(int i=l ; i>=0 ; i--){
            if((k&(1<<i)) != 0){
                u = up[u][i];
            }
        }
        return u;
    }
}
