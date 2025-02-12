import java.util.ArrayList;

public class DSU {
    int[] parent;
    int[] setSize;

    /* 
       if you call sets[findSet(x)], then sets[findSet(x)] is an arraylist that
       include all the elements in the set that x is in.
    */
    ArrayList<Integer>[] sets;

    DSU (int n) {
        n++;
        parent = new int[n];
        setSize = new int[n];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

    }

    boolean unionSet(int x, int y) {
        int repX = findSet(x), repY = findSet(y);

        if (repX == repY) {
            return false;
        }

        if (setSize[repX] > setSize[repY]) {
            parent[repY] = repX;
            setSize[repX] += setSize[repY];
            
            for(int i = 0; i < setSize[repY]; i++) {
                sets[repX].add(sets[repY].get(i));
            }

        } else {
            parent[repX] = repY;
            setSize[repY] += setSize[repX];

            for(int i = 0; i < setSize[repX]; i++) {
                sets[repY].add(sets[repX].get(i));
            }
        }
        return true;
    }

    int findSet (int x) {
        if (parent[x] == x) {
            return x;
        }
        return findSet(parent[x]);
    }

}