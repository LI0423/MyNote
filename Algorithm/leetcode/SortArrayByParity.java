package Algorithm.leetcode;

//905. 按奇偶排序数组

// 给你一个整数数组 nums，将 nums 中的的所有偶数元素移动到数组的前面，后跟所有奇数元素。
// 返回满足此条件的 任一数组 作为答案。

// 示例 1：

// 输入：nums = [3,1,2,4]
// 输出：[2,4,3,1]
// 解释：[4,2,3,1]、[2,4,1,3] 和 [4,2,1,3] 也会被视作正确答案。
// 示例 2：

// 输入：nums = [0]
// 输出：[0]

public class SortArrayByParity {

    // 定义一个新数组，遍历寻找偶数添加到数组左边，奇数添加到数组右边
    public int[] sortArrayByParity(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int left = 0, right = n - 1;
        for (int num : nums){
            if (num % 2 == 0){
                res[left++] = num;
            } else {
                res[right--] = num;
            }
        }
        return res;
    }

    // 定义一个新数组，两次遍历，第一次遍历找到偶数添加到数组中，第二次遍历找到奇数添加到数组中
    public int[] sortArrayByParity1(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int i = 0;
        for (int num : nums) {
            if (num % 2 == 0) {
                res[i] = num;
            }
        }
        for (int num : nums) {
            if (num % 2 != 0) {
                res[i] = num;
            }
        }
        return res;
    }

    // 双指针，左指针寻找奇数，右指针寻找偶数，当都找到时原地交换。
    public int[] sortArrayByParity2(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            while (left < right && nums[left] % 2 == 0) {
                left++;
            }
            while (left < right && nums[right] % 2 != 0) {
                right--;
            }
            if (left < right) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                left++;
                right--;
            }
        }
        return nums;
    }
    
}
