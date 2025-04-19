package lab_08;

import java.util.ArrayList;

public class TreeDiameter {
    int n;
    ArrayList<Integer>[] adjL;
    public TreeDiameter(int n, ArrayList<Integer>[] adjL){
        this.n = n;
        this.adjL = adjL;
    }
    public int getDiameter(){
        /*
            (1) For any node in the tree, any one of the furthest nodes
               from it is an endpoint of a diameter.
            (2) For any two nodes u, v representing endpoints of a tree
               diameter, all nodes in the tree have one of u or v as
               one of the furthest nodes from them.
        */
        int[] furthestNodeA = getFurthestNode(0);
        int[] furthestNodeB = getFurthestNode(furthestNodeA[0]);
        return furthestNodeB[1];
    }
    public int[] getFurthestNode(int u){
        // returns {furthestNode from u, dist from u to that node}
        return getFurthestNode(u, -1);
    }
    private int[] getFurthestNode(int cur, int par){
        int[] ans = new int[]{cur, 0};
        for(int nxt : adjL[cur]){
            if(nxt == par){
                continue;
            }
            int[] nxtAns = getFurthestNode(nxt, cur);
            if(nxtAns[1]+1 > ans[1]){
                ans[0] = nxtAns[0];
                ans[1] = nxtAns[1]+1;
            }
        }
        return ans;
    }
}

