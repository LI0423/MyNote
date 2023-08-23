package Algorithm.leetcode;

//747. 至少是其他数字两倍的最大数

// 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
// 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。如果是，则返回 最大元素的下标 ，否则返回 -1 。

// 示例 1：

// 输入：nums = [3,6,1,0]
// 输出：1
// 解释：6 是最大的整数，对于数组中的其他整数，6 至少是数组中其他元素的两倍。6 的下标是 1 ，所以返回 1 。
// 示例 2：

// 输入：nums = [1,2,3,4]
// 输出：-1
// 解释：4 没有超过 3 的两倍大，所以返回 -1 。
// 示例 3：

// 输入：nums = [1]
// 输出：0
// 解释：因为不存在其他数字，所以认为现有数字 1 至少是其他数字的两倍。

public class DominantIndex {
    public int dominantIndex(int[] nums) {
        if (nums.length == 1){
            return 0;
        }
        int max = nums[0];
        int index = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] > max){
                max = nums[i];
                index = i;
            }
        }
        for(int i = 0; i < nums.length; i++){
            if(index == i || nums[i] == 0){
                continue;
            }
            if(max / nums[i] >= 2){
                continue;
            } else{
                return -1;
            }
        }
        return index;
    }

    public int dominantIndex1(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        int a = -1, b = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[b]) {
                a = b; b = i;
            } else if (a == -1 || nums[i] > nums[a]) {
                a = i;
            }
        }
        return nums[b] >= nums[a] * 2 ? b : -1;
    }

}

//Python

// class Solution:
//     def dominantIndex(self, nums: List[int]) -> int:
//         n = len(nums)
//         if n == 1:
//             return 0
//         a, b = -1, 0
//         for i in range(1, n):
//             if nums[i] > nums[b]:
//                 a, b = b, i
//             elif a == -1 or nums[i] > nums[a]:
//                 a = i
//         return b if nums[b] >= nums[a] * 2 else -1

//Golang

// func dominantIndex(nums []int) int {
//     n := len(nums)
//     if n == 1{
//         return 0
//     }
//     a, b := -1, 0
//     for i := 1; i < n; i++ {
//         if nums[i] > nums[b] {
//             a, b = b, i
//         } else if a == -1 || nums[i] > nums[a] {
//             a = i
//         }
//     }
//     if nums[b] >= nums[a] * 2{
//         return b
//     }
//     return -1
// }
