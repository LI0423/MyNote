package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 15. 三数之和
 * https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0015.3Sum/README.md

题目描述
给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
注意：答案中不可以包含重复的三元组。

示例 1：
输入：nums = [-1,0,1,2,-1,-4]
输出：[[-1,-1,2],[-1,0,1]]

示例 2：
输入：nums = []
输出：[]

示例 3：
输入：nums = [0]
输出：[]

解题思路：使用双指针。能够使用双指针（头尾指针）搜索目标是因为数组是有序的，当移动指针时，数值的变化可预测。当找到目标值后，l与r都需要进行移动，并且是
    移动到不等于组合时的值。如果nums[l]==0,那么l需要移动至nums[l]!=0的位置，r同理。
 */

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums){
        int n = nums.length;
        if(n<3){
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i < n-2 && nums[i] <= 0; ++i ){
            if(i>0 && nums[i] == nums[i-1]){
                continue;
            }
            int j = i+1,k=n-1;
            while(j<k){
                if(nums[i] + nums[j] + nums[k] == 0){
                    res.add(Arrays.asList(nums[i],nums[j],nums[k]));
                    ++j;
                    --k;
                    while(j<n && nums[j] == nums[j-1]){
                        ++j;
                    }
                    while(k>i && nums[k] == nums[k+1]){
                        --k;
                    }
                }else if(nums[i] + nums[j] + nums[k] < 0){
                    ++j;
                }else{
                    --k;
                }
            }
        }
        return res;
    }
}
