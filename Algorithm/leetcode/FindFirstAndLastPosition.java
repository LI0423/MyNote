package Algorithm.leetcode;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 target，返回 [-1, -1]。

示例 1：

输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]
示例 2：

输入：nums = [5,7,7,8,8,10], target = 6
输出：[-1,-1]
示例 3：

输入：nums = [], target = 0
输出：[-1,-1]
 */

public class FindFirstAndLastPosition {

    public int[] searchRange(int[] nums,int target){
        int l = search(nums, target);
        int r = search(nums, target+1);
        return l == nums.length || l>=r? new int[]{-1,-1} : new int[]{l,r-1};
    }

    private int search(int[] nums,int target){
        int left = 0,right=nums.length;
        while(left<right){
            int mid = (left + right) >>> 1;
            if(nums[mid] >= target){
                right = mid;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }
    
}
