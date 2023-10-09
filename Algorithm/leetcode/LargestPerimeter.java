package Algorithm.leetcode;

import java.util.Arrays;

//976. 三角形的最大周长

// 给定由一些正数（代表长度）组成的数组 nums ，返回 由其中三个长度组成的、面积不为零的三角形的最大周长 。如果不能形成任何面积不为零的三角形，返回 0。

// 示例 1：

// 输入：nums = [2,1,2]
// 输出：5
// 解释：你可以用三个边长组成一个三角形:1 2 2。
// 示例 2：

// 输入：nums = [1,2,1,10]
// 输出：0
// 解释：
// 你不能用边长 1,1,2 来组成三角形。
// 不能用边长 1,1,10 来构成三角形。
// 不能用边长 1、2 和 10 来构成三角形。
// 因为我们不能用任何三条边长来构成一个非零面积的三角形，所以我们返回 0。

public class LargestPerimeter {

    // 将边长数组进行递增排序，然后倒序进行遍历，当满足条件两边之和大于第三边时即构成三角形，此时边长最大。
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i-1] + nums[i-2] > nums[i]) {
                return nums[i-1] + nums[i-2] + nums[i];
            }
        }
        return 0;
    }
    
}
