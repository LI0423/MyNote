package Algorithm.leetcode;

/**
 * 53. 最大子序和

题目描述
给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
子数组 是数组中的一个连续部分。

示例 1：
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。

示例 2：
输入：nums = [1]
输出：1

示例 3：
输入：nums = [5,4,-1,7,8]
输出：23

解题思路：
（1）动态规划：设dp[i]表示[0...i]中，以nums[i]结尾的最大子数组和，状态转移方程dp[i] = nums[i] + max(dp[i-1],0)。
（2）分治算法：最大子序可能有三种情况，在数组左半部分，在数组右半部分，跨越左右半部分。
 */

public class MaxSubarray {
    //动态规划
    public int maximumSubarray(int[] nums){
        int f = nums[0],res = nums[0];
        for(int i = 1,n = nums.length;i<n;++i){
            f = nums[i] + Math.max(f, 0);
            res = Math.max(res, f);
        }
        return res;
    }

    //分治算法
    public int maxSubarray(int[] nums){
        return maxSub(nums, 0, nums.length-1);
    }
    private int maxSub(int[] nums,int left,int right){
        if(left == right){
            return nums[left];
        }
        int mid = (left + right) >>> 1;
        int lsum = maxSub(nums, left, mid);
        int rsum = maxSub(nums, mid+1, right);
        return Math.max(Math.max(lsum, rsum), crossMaxSub(nums, left, mid, right));
    }
    private int crossMaxSub(int[] nums,int left,int mid,int right){
        int lsum=0,rsum=0;
        int lmax = Integer.MIN_VALUE,rmax = Integer.MIN_VALUE;
        for(int i = mid;i>=left;--i){
            lsum += nums[i];
            lmax = Math.max(lmax, lsum);
        }
        for(int i = mid + 1;i<=right;++i){
            rsum += nums[i];
            rmax = Math.max(rmax, rsum);
        }
        return lmax + rmax;
    }
}
