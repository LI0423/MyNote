package Algorithm.leetcode;

public class Maximum69Number {

    public static int maximum69Number(int num){
        return Integer.valueOf(String.valueOf(num).replaceFirst("6", "9"));
    }

    public static void main(String[] args) {
        int num = 9669;
        System.out.println(maximum69Number(num));
    }
}
