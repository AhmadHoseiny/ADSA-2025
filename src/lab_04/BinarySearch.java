package lab_04;

public class BinarySearch {

    /*
        Sample Binary Search Implementation
    */
    public static boolean check(int mid){
        return true;
    }
    public static void binarySeacrh(){
        int l=0, r=(int)1e9;
        while(l<=r){
            int mid = l + (r-l)/2;
            if(check(mid)){
                r = mid-1;
            }else{
                l = mid+1;
            }
        }
        /*
            after the loop terminates:
            (1) l = r+1
            (2) l is the smallest value for which check(l) is true
            (3) r is the largest value for which check(r) is false
        */
    }


    /*
        Problems discussed in lecture:
        (1) https://codeforces.com/problemset/problem/165/B
        (2) Given n points on a 2d coordinate plane where 1<=n<=1e5,
           you are asked to find the minimum radius r of a circle centered
           at the origin such that all n points lie inside the circle. The
           absolute error should not exceed 1e-6.
     */

}
