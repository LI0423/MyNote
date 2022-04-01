package Algorithm.leetcode;

/**
 * 69. x 的平方根

题目描述
给你一个非负整数 x ，计算并返回 x 的 算术平方根 。

由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。

示例 1：
输入：x = 4
输出：2

示例 2：
输入：x = 8
输出：2
解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
 */

public class SqrtX {
    
    public static int sqrtX(int x){
        int left = 0,right = x;
        while(left<right){
            int mid = (left + right + 1)>>>1;
            if(mid <= x/mid){
                left = mid;
            }else{
                right = mid-1 ;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        sqrtX(8);
    }

}
