package Algorithm.leetcode;

import java.util.Arrays;

// 977. 有序数组的平方

// 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。

// 示例 1：

// 输入：nums = [-4,-1,0,3,10]
// 输出：[0,1,9,16,100]
// 解释：平方后，数组变为 [16,1,0,9,100]
// 排序后，数组变为 [0,1,9,16,100]
// 示例 2：

// 输入：nums = [-7,-3,2,3,11]
// 输出：[4,9,9,49,121]

public class SortedSquares {

    // 双指针，左右两边计算数字的平方，如果左边大于右边就将左边放入结果数组中，左指针向后移一位；
    // 如果右边大于左边就将右边放入结果数组中，右指针向前移一位，依此类推。
    public int[] sortedSquares(int[] nums) {
        int i = 0, j = nums.length - 1;
        int index = nums.length - 1;
        int[] res = new int[nums.length];
        while (i <= j) {
            int a = nums[i] * nums[i];
            int b = nums[j] * nums[j];
            if (a > b) {
                res[index--] = a;
                i++;
            } else {
                res[index--] = b;
                j--;
            }
        }
        return res;
    }

    // 直接求数组的平方，放入结果数组中，然后对结果数组进行排序。
    public int[] sortedSquares2(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++){
            res[i] = nums[i] * nums[i];
        }
        Arrays.sort(res);
        return res;
    }

    // 双指针，该数组已按照非递减顺序排序，如果全为负数，平方后得到的数组单调递减，如果全为正数，平方后得到的数组单调递增，
    // 经过一次遍历后可以找到负数和正数的分界线，就可以利用归并进行排序，每次比较两个指针对应的数，选择较小的那个放入答案并移动指针。
    // 当某一指针移至边界时，将另一指针还未遍历到的数依次放入答案。
    public int[] sortedSquares3(int[] nums) {
        int n = nums.length;
        int negative = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] < 0) {
                negative = i;
            } else {
                break;
            }
        }
        int[] ans = new int[n];
        int index = 0, i = negative, j = negative + 1;
        while (i >= 0 || j < n) {
            if (i < 0) {
                ans[index] = nums[j] * nums[j];
                ++j;
            } else if (j == n) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else if (nums[i] * nums[i] < nums[j] * nums[j]) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else {
                ans[index] = nums[j] * nums[j];
                ++j;
            }
            ++index;
        }

        return ans;
    }
}


// Python

// class SortedSquares:
//     def sortedSquares(self, nums: List[int]) -> List[int] :
//         n = len(nums)
//         negative = -1
//         for i, num in enumerate(nums):
//             if num < 0:
//                 negative = i
//             else:
//                 break
//         ans = list()
//         i, j = negative, negative + 1
//         while i >= 0 or j < n:
//             if i < 0:
//                 ans.append(nums[j] * nums[j])
//                 j += 1
//             elif j == n:
//                 ans.append(nums[i] * nums[i])
//                 i -= 1
//             elif nums[i] * nums[i] < nums[j] * nums[j]:
//                 ans.append(nums[i] * nums[i])
//                 i -= 1
//             else:
//                 ans.append(nums[j] * nums[j])
//                 j += 1
//         return ans
    
//     def sortedSquares2(self, nums: List[int]) -> List[int]:
//         n = len(nums)
//         i, j, pos = 0, n - 1, n - 1
//         ans = [0] * n
//         while i <= j:
//             if nums[i] * nums[i] < nums[j] * nums[j]:
//                 ans[pos] = nums[j] * nums[j]
//                 j -= 1
//             else:
//                 ans[pos] = nums[i] * nums[i]
//                 i += 1
//             pos -= 1
//         return ans

//     def sortedSquares3(self, nums: List[int]) -> List[int]:
//         return sorted(num * num for num in nums)


// Golang

// func sortedSquares(nums []int) []int {
//     ans := make([]int, len(nums))
//     for i, v := range nums {
//         ans[i] = v * v
//     }
//     sort.Ints(ans)
//     return ans
// }

// func sortedSquares2(nums []int) []int {
//     n := len(nums)
//     lastNegative := -1
//     for i := 0; i < n && nums[i] < 0; i++ {
//         lastNegative++
//     }

//     ans := make([]int, 0, n)
//     for i, j := lastNegative, lastNegative+1; i >= 0 || j < n; {
//         if i < 0 {
//             ans = append(ans, nums[j] * nums[j])
//             ++j
//         } else if j == n {
//             ans = append(ans, nums[i] * nums[i])
//             --i
//         } else if nums[i] * nums[i] < nums[j] * nums[j] {
//             ans = append(ans, nums[j] * nums[j])
//             --i
//         } else {
//             ans = append(ans, nums[i] * nums[i])
//             ++j
//         }
//     }
//     return ans
// }

// func sortedSquares3(nums []int) []int {
//     n := len(nums)
//     ans := make([]int, n)
//     i, j := 0, n-1
//     for pos := n-1; pos >= 0; pos-- {
//         if v, w := nums[i] * nums[i], nums[j] * nums[j]; v > w {
//             ans[pos] = v
//             i++
//         } else {
//             ans[pos] = w
//             j--
//         }
//         pos--
//     } 
//     return ans
// }