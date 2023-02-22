package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//228. 汇总区间

// 给定一个  无重复元素 的 有序 整数数组 nums 。
// 返回恰好覆盖数组中所有数字的最小有序区间范围列表。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。
// 列表中的每个区间范围 [a,b] 应该按如下格式输出：
// "a->b" ，如果 a != b
// "a" ，如果 a == b

// 示例 1：

// 输入：nums = [0,1,2,4,5,7]
// 输出：["0->2","4->5","7"]
// 解释：区间范围是：
// [0,2] --> "0->2"
// [4,5] --> "4->5"
// [7,7] --> "7"
// 示例 2：

// 输入：nums = [0,2,3,4,6,8,9]
// 输出：["0","2->4","6","8->9"]
// 解释：区间范围是：
// [0,0] --> "0"
// [2,4] --> "2->4"
// [6,6] --> "6"
// [8,9] --> "8->9"

public class SummaryRanges {
    public List<String> summaryRanges(int[] nums){
        List<String> res = new ArrayList<>();
        for (int i = 0, j = 0; j < nums.length; j++){
            if (j + 1 < nums.length && nums[j+1] == nums[j] + 1){
                continue;
            }
            if (i == j) {
                res.add(nums[i] + "");
            } else {
                res.add(nums[i] + "->" + nums[j]);
            }
            i = j + 1;
        }
        return res;
    }
}

//Golang

// import "strconv"

// func summaryRanges(nums []int) (ans []string) {
//     for i, n := 0, len(nums); i < n; {
//         left := i
//         for i++; i < n && nums[i-1]+1 == nums[i]; i++ {
//         }
//         s := strconv.Itoa(nums[left])
//         if left < i-1 {
//             s += "->" + strconv.Itoa(nums[i-1])
//         }
//         ans = append(ans, s)
//     }
//     return
// }
