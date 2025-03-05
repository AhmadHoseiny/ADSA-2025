package lab_04;
import java.util.HashSet;

public class MeetInTheMiddle {

    /*
        Problems discussed in lecture:
        (1) Given an array of n integers, where 1<=n<=2000,
           count the number of increasing subsequences of
           length 3 [bonus: 1<=n<=1e5].
        (2) 4SUM: Given an array of integers find 4 (distinct)
           numbers whose sum is target x.
        (3) Given an array of n integers, where 1<=n<=40,
           check whether there is a subset whose sum is target x.
     */


    /*
        Code for 3rd Problem:
        Given an array of n integers, check whether
        there is a subset whose sum is equal to k.
        1 <= n <= 40
        1 <= k <= 1e9
        1 <= arr[i] <= 1e9
    */

    static int n, k;
    static int[] arr;
    public static void genSubsetSums(int idx, int end, long sum, HashSet<Long> hs){
        if(idx == end){
            hs.add(sum);
            return;
        }
        genSubsetSums(idx+1, end, sum, hs);
        genSubsetSums(idx+1, end, sum+arr[idx], hs);
    }
    public static HashSet<Long> genSubsetSums(int start, int end) {
        HashSet<Long> hs = new HashSet<>();
        genSubsetSums(start, end, 0, hs);
        return hs;
    }

    public static boolean solve(){
        HashSet<Long> hsLeft = genSubsetSums(0, n/2);
        HashSet<Long> hsRight = genSubsetSums(n/2, n);
        for(long x : hsLeft){
            if(hsRight.contains(k-x)){
                return true;
            }
        }
        return false;
    }

}
