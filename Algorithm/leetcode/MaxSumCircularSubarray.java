package Algorithm.leetcode;

/**
 * 918. 环形子数组的最大和

题目描述
给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。

示例 1：
输入：nums = [1,-2,3,-2]
输出：3
解释：从子数组 [3] 得到最大和 3

示例 2：
输入：nums = [5,-3,5]
输出：10
解释：从子数组 [5,5] 得到最大和 5 + 5 = 10

示例 3：
输入：nums = [3,-2,2,-3]
输出：3
解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3

解题思路：环形子数组的最大和可分为两种情况，无环最大和、有环最大和。有环最大和可以转换为求最小子序和s2，然后用sum减去最小子序和，得到有环的最大和。
 */

public class MaxSumCircularSubarray {
    
    public int maxSumCircularSubarray(int[] nums){
        int s1=nums[0],s2=nums[0],f1=nums[0],f2=nums[0],total=nums[0];
        for(int i = 1;i<nums.length;++i){
            total += nums[i];
            f1 = nums[i]+Math.max(f1, 0);
            f2 = nums[i]+Math.min(f2, 0);
            s1 = Math.max(s1, f1);
            s2 = Math.min(s2, f2);
        }
        return s1 > 0 ? Math.max(s1, total-s2) : s1;
    }

}
