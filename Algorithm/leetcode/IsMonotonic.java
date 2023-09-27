package Algorithm.leetcode;

//896. 单调数列

// 如果数组是单调递增或单调递减的，那么它是 单调 的。
// 如果对于所有 i <= j，nums[i] <= nums[j]，那么数组 nums 是单调递增的。 如果对于所有 i <= j，nums[i]> = nums[j]，那么数组 nums 是单调递减的。
// 当给定的数组 nums 是单调数组时返回 true，否则返回 false。

// 示例 1：

// 输入：nums = [1,2,2,3]
// 输出：true
// 示例 2：

// 输入：nums = [6,5,4,4]
// 输出：true
// 示例 3：

// 输入：nums = [1,3,2]
// 输出：false

public class IsMonotonic {

    public boolean isMonotonic(int[] nums) {
        return isSorted(nums, true) || isSorted(nums, false);
    }

    public boolean isSorted(int[] nums, boolean increasing){
        int n = nums.length;
        if (increasing) {
            for (int i = 1; i < n; i++){
                if (nums[i] < nums[i-1]){
                    return false;
                }
            }
        } else {
            for (int i = 1; i < n; i++){
                if (nums[i] > nums[i-1]){
                    return false;
                }
            }
        }
        return true;
    }

    // 定义升序和降序标识，一次遍历，如果为升序，inc为true，如果是降序，desc为true，如果都不是则都为false
    public boolean isMonotonic2(int[] nums) {
        boolean inc = true, desc = true;
        int n = nums.length;
        for (int i = 1; i < n; i++){
            if (nums[i] < nums[i - 1]) {
                inc = false;
            }
            if (nums[i] > nums[i - 1]) {
                desc = false;
            }
        }
        return inc || desc;
    }
    
}


