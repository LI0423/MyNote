package Algorithm.leetcode.greedy;

import java.util.Arrays;

/**
 * 2160. 拆分数位后四位数字的最小和

给你一个四位 正 整数 num 。请你使用 num 中的 数位 ，将 num 拆成两个新的整数 new1 和 new2 。new1 和 new2 中可以有 前导 0 ，且 num 中 所有 数位都必须使用。
比方说，给你 num = 2932 ，你拥有的数位包括：两个 2 ，一个 9 和一个 3 。一些可能的 [new1, new2] 数对为 [22, 93]，[23, 92]，[223, 9] 和 [2, 329] 。
请你返回可以得到的 new1 和 new2 的 最小 和。

示例 1：
输入：num = 2932
输出：52
解释：可行的 [new1, new2] 数对为 [29, 23] ，[223, 9] 等等。
最小和为数对 [29, 23] 的和：29 + 23 = 52 。

示例 2：
输入：num = 4009
输出：13
解释：可行的 [new1, new2] 数对为 [0, 49] ，[490, 0] 等等。
最小和为数对 [4, 9] 的和：4 + 9 = 13 。
 */

public class MinimumSum {

    public static int minimumSum(int num){
        int a = num%10;
        int b = num/10%10;
        int c = num/100%10;
        int d = num/1000;
        int[] res = new int[]{a,b,c,d};
        Arrays.sort(res);
        return res[0]*10+res[1]*10+res[2]+res[3];
    }

    public static void main(String[] args) {
        int num = 2932;
        int minimumSum = minimumSum(num);
        System.out.println(minimumSum);
    }
}
