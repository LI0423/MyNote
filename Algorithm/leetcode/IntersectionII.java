package Algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//350. 两个数组的交集 II

// 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。

// 示例 1：

// 输入：nums1 = [1,2,2,1], nums2 = [2,2]
// 输出：[2,2]
// 示例 2:

// 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
// 输出：[4,9]

public class IntersectionII {
    public int[] intersection(int[] nums1, int[] nums2){
        if (nums1.length > nums2.length) {
            return intersection(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : nums1) {
            int count = map.getOrDefault(i, 0) + 1;
            map.put(i, count);
        }
        int[] res = new int[nums1.length];
        int i = 0;
        for (Integer item : nums2) {
            int count = map.getOrDefault(item, 0);
            if (count > 0) {
                res[i++] = item;
                count--;
                if (count > 0){
                    map.put(item, count);
                } else {
                    map.remove(item);
                }
            }
        }
        return Arrays.copyOfRange(res, 0, i);
    }
}

//Golang

// func intersection(nums1, nums2 []int) []int {
//     if len(nums1) > len(nums2) {
//         return intersection(nums2, nums1);
//     }
//     m := map[int]int{}
//     for _, num := range nums1 {
//         m[num]++
//     }
//     res := []int{}
//     for _, num := range nums2 {
//         if m[num] > 0 {
//             res = append(res, num)
//             m[num]--
//         }
//     }
//     return res
// }