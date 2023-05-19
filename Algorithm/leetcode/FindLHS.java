package Algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//594. 最长和谐子序列

// 和谐数组是指一个数组里元素的最大值和最小值之间的差别 正好是 1 。
// 现在，给你一个整数数组 nums ，请你在所有可能的子序列中找到最长的和谐子序列的长度。
// 数组的子序列是一个由数组派生出来的序列，它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。

// 示例 1：

// 输入：nums = [1,3,2,2,5,2,3,7]
// 输出：5
// 解释：最长的和谐子序列是 [3,2,2,2,3]
// 示例 2：

// 输入：nums = [1,2,3,4]
// 输出：2
// 示例 3：

// 输入：nums = [1,1,1,1]
// 输出：0















public class FindLHS {

    //先对 nums 进行排序，然后从前往后使用「双指针」实现「滑动窗口」进行扫描，统计所有符合条件的窗口长度，并在所有长度中取最大值即是答案。
    public int findLHS(int[] nums) {
        if(nums == null) {
            return 0;
        }
        Arrays.sort(nums);
        int n = nums.length, ans = 0;
        for(int i = 0, j = 0; j < n; j++){
            while(i < j && nums[j] - nums[i] > 1) i++;
            if(nums[j] - nums[i] == 1) ans = Math.max(ans, j-i+1);
        }
        return ans;
    }

    //使用「哈希表」记录所有 nums[i] 的出现次数，然后找出所有可能的数对（两数差值为 1)，并在所有符合条件的数对所能构成的「和谐子序列」长度中取最大值。
    public int findLHS2(int[] nums){
        if(nums == null){
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for(int n : nums){
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int ans = 0;
        for(int n : nums){
            if(map.containsKey(n - 1)){
                ans = Math.max(ans, map.get(n) + map.get(n - 1));
            }
        }
        return ans;
    }
}
