package Algorithm.leetcode;

/**
 * 152. 乘积最大子数组

题目描述
给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
测试用例的答案是一个 32-位 整数。
子数组 是数组的连续子序列。

示例 1:
输入: nums = [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。

示例 2:
输入: nums = [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。

解题思路：考虑当前位置i的值，如果是一个负数，则希望以它前一个位置结尾的某个段的积也是个负数，可以负负得正，并希望尽可能小；如果是一个正数，
    则希望以它前一个位置结尾的某个段的积也是正数，并且尽可能的大。分别维护fmax和fmin。
    fmax(i) = max(nums[i],fmax(i-1)*nums[i],fmin(i-1)*nums[i])
    fmin(i) = min(nums[i],fmax(i-1)*nums[i],fmin(i-1)*nums[i])
    res = max(fmax(i))
 */

public class MaxProductSubarray {
    public int maxProductSubarray(int[] nums){
        int fmax = nums[0],fmin = nums[0],res = nums[0];
        for(int i=1;i<nums.length;++i){
            int m = fmax,n = fmin;
            fmax = Math.max(nums[i], Math.max(m*nums[i], n*nums[i]));
            fmin = Math.min(nums[i], Math.min(m*nums[i], n*nums[i]));
            res = Math.max(res, fmax);
        }
        return res;
    }
}
