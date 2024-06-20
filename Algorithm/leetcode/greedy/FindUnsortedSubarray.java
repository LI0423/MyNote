package Algorithm.leetcode.greedy;

import java.util.Arrays;

/**
 * 581. 最短无序连续子数组

给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
请你找出符合题意的 最短 子数组，并输出它的长度。

示例 1：
输入：nums = [2,6,4,8,10,9,15]
输出：5
解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。

示例 2：
输入：nums = [1,2,3,4]
输出：0

示例 3：
输入：nums = [1]
输出：0
 */

public class FindUnsortedSubarray {

    public static int findUnsortedSubarray(int[] nums){
        if (isSorted(nums)){
            return 0;
        }
        int[] numsSorted = new int[nums.length];
        System.arraycopy(nums, 0, numsSorted, 0, nums.length);
        Arrays.sort(numsSorted);
        int left = 0, right = nums.length - 1;
        while (nums[left] == numsSorted[left]){
            left++;
        }
        while (nums[right] == numsSorted[right]){
            right--;
        }
        return right - left + 1;
    }

    public static boolean isSorted(int[] nums){
        if (nums.length == 1){
            return true;
        }
        for (int i = 0; i < nums.length - 1; i++){
            if (nums[i] > nums[i + 1]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println(findUnsortedSubarray(nums));
    }
}
