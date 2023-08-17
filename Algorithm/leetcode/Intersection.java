package Algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

//349. 两个数组的交集

// 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。

// 示例 1：

// 输入：nums1 = [1,2,2,1], nums2 = [2,2]
// 输出：[2]
// 示例 2：

// 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
// 输出：[9,4]
// 解释：[4,9] 也是可通过的

public class Intersection {
    public int[] intersection(int[] nums1, int[] nums2){
        Set<Integer> set = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for(int item : nums1){
            set.add(item);
        }
        for(int n : nums2){
            if (set.contains(n)){
                set2.add(n);
            }
        }
        int[] arr = new int[set2.size()];
        int i = 0;
        for(Integer n : set2){
            System.out.println(n);
            arr[i++] = n;
        }
        return arr;
    }
}

//Golang

// func intersection(nums1, nums2 []int) []int {
//     set1 := map[int]struct{}{}
//     set2 := map[int]struct{}{}
//     for _,item1 := range nums1 {
//         set1[item1] = struct{}{}
//     }
//     for _,item2 := range nums2 {
//         if _, has := set1[item2]; has {
//             set2[item2] = struct{}{}
//         }
//     }
//     res := []int{}
//     for i := range set2 {
//         res = append(res, i)
//     }
//     return res
// }
