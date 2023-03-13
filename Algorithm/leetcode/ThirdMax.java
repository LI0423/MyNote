package Algorithm.leetcode;

import java.util.Arrays;
import java.util.TreeSet;

//414. 第三大的数

// 给你一个非空数组，返回此数组中 第三大的数 。如果不存在，则返回数组中最大的数。 

// 示例 1：

// 输入：[3, 2, 1]
// 输出：1
// 解释：第三大的数是 1 。
// 示例 2：

// 输入：[1, 2]
// 输出：2
// 解释：第三大的数不存在, 所以返回最大的数 2 。
// 示例 3：

// 输入：[2, 2, 3, 1]
// 输出：1
// 解释：注意，要求返回第三大的数，是指在所有不同数字中排第三大的数。
// 此例中存在两个值为 2 的数，它们都排第二。在所有不同数字中排第三大的数为 1 。

public class ThirdMax {
    //将数组从大到小排序后，从头开始遍历数组，通过判断相邻元素是否不同，来统计不同元素的个数。
    //如果能找到三个不同的元素，就返回第三大的元素，否则返回最大的元素。
    public int thirdMax(int[] nums){
        Arrays.sort(nums);
        reverse(nums);
        for (int i = 1, diff = 1; i < nums.length; ++i) {
            if (nums[i] != nums[i-1] && ++diff == 3){
                return nums[i];
            }
        }
        return nums[0];
    }

    public void reverse(int[] nums){
        int left = 0, right = nums.length - 1;
        while(left < right){
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }

    //我们可以遍历数组，同时用一个有序集合来维护数组中前三大的数。具体做法是每遍历一个数，就将其插入有序集合，
    //若有序集合的大小超过 333，就删除集合中的最小元素。这样可以保证有序集合的大小至多为 333，且遍历结束后，
    //若有序集合的大小为 333，其最小值就是数组中第三大的数；
    //若有序集合的大小不足 333，那么就返回有序集合中的最大值。
    public int thirdMax2(int[] nums){
        TreeSet<Integer> treeSet = new TreeSet<>();
        for(int num : nums) {
            treeSet.add(num);
            if(treeSet.size() > 3){
                treeSet.remove(treeSet.first());
            }
        }
        return treeSet.size() == 3 ? treeSet.first() : treeSet.last();
    }
}

//Golang

// func thirdMax(nums []int) int {
//     sort.Sort(sort.Reverse(sort.IntSlice(nums)))
//     for i, diff := 1, 1; i < len(nums); i++ {
//         if nums[i] != nums[i-1] {
//             diff++
//             if diff == 3{
//                 return nums[i]
//             }
//         }
//     } 
//     return nums[0]
// }


// func thirdMax2(nums []int) int {
//     t := redblacktree.NewWithIntComparator()
//     for _, num := range nums {
//         t.put(num, nil)
//         if t.Size() > 3 {
//             t.Remove(t.Left.Key)
//         }
//     }
//     if t.Siza() == 3 {
//         return t.Left().Key.(int)
//     }
//     return t.Right().Key.(int)
// }
