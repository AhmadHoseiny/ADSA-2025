package lab_05;

import java.util.Arrays;

public class DynamicProgramming {

    //Fibonacci problem
            /*

            3
           / \
           2  1
          / \
         1   0

         */
    // 0 1 1 2 3 5 8 ...
    static int[] dp = new int[1005];

    static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        if (dp[n] != -1) {
            return dp[n];
        }
        return dp[n] = fib(n - 1) + fib(n - 2);
    }


    //Knapsack problem
    static int[][] dp2 = new int[105][1005];
    static int[] V = new int[1005];
    static int[] W = new int[1005];
    static int arraySize;

    // O(100*1000*2)
    static int knapsack(int idx /*100*/, int weight/*1000*/) {
        if (weight < 0) {
            return Integer.MIN_VALUE;
        }
        if (idx == arraySize) {
            return 0;
        }
        if (dp2[idx][weight] != -1) {
            return dp2[idx][weight];
        }
        // take
        int take = knapsack(idx + 1, weight - W[idx]) + V[idx];
        // leave
        int leave = knapsack(idx + 1, weight);
        int answer = Math.max(take, leave);
        return dp2[idx][weight] = answer;
    }

    // TSP
    static int cities;
    static int[][] weights;
    static int[][] dp3 = new int[18+5][(1 << 18)+5];
    // o(cities ^ 2 * 2 ^ cities)
    static int TSP(int city, int mask) {
        if ((1 << cities) - 1 == mask) {
            return 0;
        }
        if (dp3[city][mask] != -1) {
            return dp3[city][mask];
        }
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < cities; i++) {
            if (!checkBit(mask, i)) {
                int takenCityMask = setBit(mask, i);
                answer = Math.min(TSP(i, takenCityMask) + weights[city][i],
                        answer);
            }
        }
        return dp3[city][mask] = answer;
    }

    static boolean checkBit(int mask, int idx) {
        return (mask >> idx & 1) == 1;
    }

    static int setBit(int mask, int idx) {
        return mask | (1 << idx);
    }

    public static void main(String[] args) {
        Arrays.fill(dp, -1);
        System.out.println(fib(5));

        arraySize = 3;
        for (int i = 0; i < 105; i++) {
            Arrays.fill(dp2[i], -1);
        }
        V[0] = 60;
        W[0] = 10;
        V[1] = 100;
        W[1] = 20;
        V[2] = 120;
        W[2] = 30;
        // I start from the first item (idx = 0) and I have 50 weight
        System.out.println(knapsack(0, 50));

        cities = 4;
        weights = new int[][]{
                {0, 10, 15, 20},
                {5, 0, 9, 10},
                {6, 13, 0, 12},
                {8, 8, 9, 0}
        };
        for (int i = 0; i < 18; i++) {
            Arrays.fill(dp3[i], -1);
        }
        // I start from the first city and I have visited the first city
        // Here depends on the problem if I need to start from the first city or not
        System.out.println(TSP(0, 1));
    }

}
