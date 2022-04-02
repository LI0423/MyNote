package Algorithm.leetcode;

import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.text.TabExpander;

/**
 * 259. 较小的三数之和

题目描述
给定一个长度为 n 的整数数组和一个目标值 target ，寻找能够使条件 nums[i] + nums[j] + nums[k] < target 成立的三元组  i, j, k 个数（0 <= i < j < k < n）。

示例 1：
输入: nums = [-2,0,1,3], target = 2
输出: 2 
解释: 因为一共有两个三元组满足累加和小于 2:
     [-2,0,1]
     [-2,0,3]

示例 2：
输入: nums = [], target = 0
输出: 0 

示例 3：
输入: nums = [0], target = 0
输出: 0
 */

public class ThreeSumSmaller {

    public int threeSumSmaller(int[] nums,int target){
        Arrays.sort(nums);
        int ans = 0;
        for(int i = 0 , n = nums.length;i<n;++i){
            int j = i+1;
            int k = n-1;
            while(j<k){
                int s = nums[i] + nums[j] + nums[k];
                if(s >= target){
                    --k;
                }else{
                    ans += k - j;
                    ++j;
                }
            }
        }
        return ans;
    }
    
}
