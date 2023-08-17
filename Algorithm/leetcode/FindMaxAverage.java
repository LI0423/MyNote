package Algorithm.leetcode;

//643. 子数组最大平均数 I

// 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
// 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
// 任何误差小于 10-5 的答案都将被视为正确答案。

// 示例 1：

// 输入：nums = [1,12,-5,-6,50,3], k = 4
// 输出：12.75
// 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
// 示例 2：

// 输入：nums = [5], k = 1
// 输出：5.00000

public class FindMaxAverage {
    public Double findMaxAverage(int[] nums, int k){
        if(nums == null) {
            return 0.0;
        }
        int n = nums.length;
        int sum = 0;
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }
        int max = sum;
        for(int i = k; i < n; i++){
            sum += nums[n-k] + nums[i+1];
            max = Math.max(max, sum);
        }
        return 1.0 * max / k;
    }
    
}
