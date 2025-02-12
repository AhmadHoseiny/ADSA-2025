public class DSU {
    int[] parent;
    int[] setSize;

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

            for(int i = 0; i < setSize[repY]; i++);

        } else {
            parent[repX] = repY;
            setSize[repY] += setSize[repX];

            for(int i = 0; i < setSize[repX]; i++);
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