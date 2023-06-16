package Algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

//697. 数组的度

// 给定一个非空且只包含非负数的整数数组 nums，数组的 度 的定义是指数组里任一元素出现频数的最大值。
// 你的任务是在 nums 中找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。

// 示例 1：

// 输入：nums = [1,2,2,3,1]
// 输出：2
// 解释：
// 输入数组的度是 2 ，因为元素 1 和 2 的出现频数最大，均为 2 。
// 连续子数组里面拥有相同度的有如下所示：
// [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
// 最短连续子数组 [2, 2] 的长度为 2 ，所以返回 2 。
// 示例 2：

// 输入：nums = [1,2,2,3,1,4,2]
// 输出：6
// 解释：
// 数组的度是 3 ，因为元素 2 重复出现 3 次。
// 所以 [2,2,3,1,4,2] 是最短子数组，因此返回 6 。

public class FindShortestSubArray {
    //先经过一次遍历，保存每个元素出现的次数，第一次出现的位置，最后一次出现的位置
    //再经过一次遍历，筛选出现次数最多的元素，并计算两个位置之间的最小值。
    public int findShortestSubArray(int[] nums){
        int n = nums.length;
        Map<Integer, int[]> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            if(map.containsKey(nums[i])){
                map.get(nums[i])[0]++;
                map.get(nums[i])[2]++;
            } else{
                map.put(nums[i], new int[]{1, i, i});
            }
        }

        int maxNum = 0, minLen = 0;
        for(Map.Entry<Integer, int[]> entry : map.entrySet()){
            int[] arr = entry.getValue();
            if(maxNum < arr[0]){
                maxNum = arr[0];
                minLen = arr[2] - arr[1] + 1;
            } else if(maxNum == arr[0]){
                if(minLen > arr[2] - arr[1] + 1){
                    minLen = arr[2] - arr[1] + 1;
                }
            }
        }
        return minLen;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2,2,3,1};
        new FindShortestSubArray().findShortestSubArray(nums);
    }
    
}
