package Algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//268. 丢失的数字

// 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。

// 示例 1：

// 输入：nums = [3,0,1]
// 输出：2
// 解释：n = 3，因为有 3 个数字，所以所有的数字都在范围 [0,3] 内。2 是丢失的数字，因为它没有出现在 nums 中。
// 示例 2：

// 输入：nums = [0,1]
// 输出：2
// 解释：n = 2，因为有 2 个数字，所以所有的数字都在范围 [0,2] 内。2 是丢失的数字，因为它没有出现在 nums 中。
// 示例 3：

// 输入：nums = [9,6,4,2,3,5,7,0,1]
// 输出：8
// 解释：n = 9，因为有 9 个数字，所以所有的数字都在范围 [0,9] 内。8 是丢失的数字，因为它没有出现在 nums 中。
// 示例 4：

// 输入：nums = [0]
// 输出：1
// 解释：n = 1，因为有 1 个数字，所以所有的数字都在范围 [0,1] 内。1 是丢失的数字，因为它没有出现在 nums 中。

public class MissingNumber {
    public int missingNumber(int[] nums){
        Set<Integer> set = new HashSet<>();
        int res = -1;
        for (int i = 0; i < nums.length; i++ ){
            set.add(nums[i]);
        }
        for (int j = 0; j <= nums.length; j++){
            if (!set.contains(j)){
                res = j;
                break;
            }
        }
        return res;
    }

    public int missingNumber2(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length; i++){
            if (nums[i] != i) {
                return i;
            }
        }
        return nums.length;
    }

}

//Golang

// func missingNumber(nums []int) int {
//     set := map[int]bool{}
//     res := -1
//     for _, v := range nums {
//         set[v] = true
//     }
//     for i := 0; ; i++ {
//         if !set[i] {
//             return i
//         }
//     }
// }

// func missingNumber2(nums []int) int {
//     sort.Ints(nums)
//     for i, num := range nums{
//         if num != i {
//             return i
//         }
//     }
//     return len(nums)
// }
