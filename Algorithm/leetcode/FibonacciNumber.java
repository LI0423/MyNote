package Algorithm.leetcode;

public class FibonacciNumber {

    public int fibonacciNumber(int n){
        int a = 0, b=1;
        while(n-- > 0){
            int c = a + b;
            a = b;
            b = c; 
        }
        return a;
    }
    
}
