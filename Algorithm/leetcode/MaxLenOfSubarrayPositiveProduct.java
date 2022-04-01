package Algorithm.leetcode;
/**
 * 1567. 乘积为正数的最长子数组长度

题目描述
给你一个整数数组 nums ，请你求出乘积为正数的最长子数组的长度。一个数组的子数组是由原数组中零个或者更多个连续数字组成的数组。
请你返回乘积为正数的最长子数组长度。

示例  1：
输入：nums = [1,-2,-3,4]
输出：4
解释：数组本身乘积就是正数，值为 24 。

示例 2：
输入：nums = [0,1,-2,-3,-4]
输出：3
解释：最长乘积为正数的子数组为 [1,-2,-3] ，乘积为 6 。
注意，我们不能把 0 也包括到子数组中，因为这样乘积为 0 ，不是正数。

示例 3：
输入：nums = [-1,-2,-3,0,1]
输出：2
解释：乘积为正数的最长子数组是 [-1,-2] 或者 [-2,-3] 。

解题思路：
 */
public class MaxLenOfSubarrayPositiveProduct {
    public static int maxLength(int[] nums){
        int f1 = nums[0] > 0 ? 1 : 0;
        int f2 = nums[0] < 0 ? 1 : 0;
        int res = f1;
        for(int i = 1;i < nums.length; ++i){
            if(nums[i] > 0){
                ++f1;
                f2 = f2 > 0 ? f2 + 1 : 0;
            }else if(nums[i] < 0){
                int pf1 = f1, pf2 = f2;
                f2 = pf1 + 1;
                f1 = pf2 > 0 ? pf2 + 1 : 0;
            }else{
                f1 = 0;
                f2 = 0;
            }
            res = Math.max(res, f1);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,-2,-3,4};
        maxLength(nums);
    }
}
