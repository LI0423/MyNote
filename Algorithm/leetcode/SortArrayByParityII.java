package Algorithm.leetcode;

//922. 按奇偶排序数组 II

// 给定一个非负整数数组 nums，  nums 中一半整数是 奇数 ，一半整数是 偶数 。
// 对数组进行排序，以便当 nums[i] 为奇数时，i 也是 奇数 ；当 nums[i] 为偶数时， i 也是 偶数 。
// 你可以返回 任何满足上述条件的数组作为答案 。

// 示例 1：

// 输入：nums = [4,2,5,7]
// 输出：[4,5,2,7]
// 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
// 示例 2：

// 输入：nums = [2,3]
// 输出：[2,3]

public class SortArrayByParityII {

    // 两次遍历，第一次遍历将i为偶数并且nums[i]为偶数的nums[i]的数字添加到新数组的偶数位，第二次遍历添加奇数。
    public int[] sortArrayByParityII(int[] nums){
        int n = nums.length;
        int[] res = new int[n];
        int index = 0;
        for (int num : nums){
            if (num % 2 == 0) {
                res[index] = num;
                index += 2;
            }
        }
        index = 1;
        for (int num : nums){
            if (num % 2 != 0) {
                res[index] = num;
                index += 2;
            }
        }
        return res;
    }

    // 双指针，i指向偶数位，j指向奇数位，如果这两位不符合规则就进行交换
    public int[] sortArrayByParityII2(int[] nums){
        int n = nums.length;
        int j = 1;
        for (int i = 0; i < n; i+=2){
            if (nums[i] % 2 == 1) {
                while (nums[j] % 2 == 1) {
                    j += 2;
                }
                swap(nums, i, j);
            }
        }
        return nums;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
