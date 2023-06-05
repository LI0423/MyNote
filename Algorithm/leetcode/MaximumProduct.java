package Algorithm.leetcode;

import java.util.Arrays;

//628. 三个数的最大乘积

// 给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。

// 示例 1：

// 输入：nums = [1,2,3]
// 输出：6
// 示例 2：

// 输入：nums = [1,2,3,4]
// 输出：24
// 示例 3：

// 输入：nums = [-1,-2,-3]
// 输出：-6

public class MaximumProduct {
    public int maximumProduct(int[] nums){
        Arrays.sort(nums);
        int n = nums.length;
        return Math.max(nums[0] * nums[1] * nums[n-1], nums[n-3] * nums[n-2] * nums[n-1]);
    }
}

//Golang

// func maximumProduct(nums []int) int {
//     sort.Ints(nums)
//     n := len(nums)
//     return max(nums[0] * nums[1] * nums[n-1], nums[n-3] * nums[n-2] * nums[n-1]);
// } 
// func max(a,b int) int {
//     if a > b {
//         return a
//     }
//     return b
// }
