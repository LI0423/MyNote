package Algorithm.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//219. 存在重复元素 II

// 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。

// 示例 1：

// 输入：nums = [1,2,3,1], k = 3
// 输出：true
// 示例 2：

// 输入：nums = [1,0,1,1], k = 1
// 输出：true
// 示例 3：

// 输入：nums = [1,2,3,1,2,3], k = 2
// 输出：false

public class ContainsNearbyDuplicate {
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++){
                if(nums[i] == nums[j] && (j-i) <= k) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    //滑动窗口控制
    public static boolean containsNearbyDuplicate3(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++){
            if(i > k) {
                set.remove(nums[i - k - 1]);
            }
            if(set.contains(nums[i])){
                return true;
            }
            set.add(nums[i]);
        }
        return false;
    }
}

//Golang

// func containsNearbyDuplicate(nums []int, k int) bool {
//     pos := map[int]int{}
//     for i, num := range nums {
//         if p, ok := pos[num]; ok, i-p <= k {
//             return true
//         }
//         pos[num] = i
//     }
//     return true
// }

// func containsNearbyDuplicate(nums []int, k int) bool {
//     set := map[int]struct{}
//     for i, num := range nums {
//         if i > k {
//             delete(i - k - 1)
//         }
//         if _, ok := set[num], ok {
//             return true
//         }
//         set[num] = struct{}{}
//     }
//     return false
// }
