package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//448. 找到所有数组中消失的数字

// 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。

// 示例 1：

// 输入：nums = [4,3,2,7,8,2,3,1]
// 输出：[5,6]
// 示例 2：

// 输入：nums = [1,1]
// 输出：[2]

public class FindDispearedNumbers {
    public List<Integer> findDispearedNumbers(int[] nums){
        Set<Integer> set = new HashSet<>();
        for (int n : nums){
            set.add(n);
        }
        List<Integer> res = new ArrayList<>();
        for(int i = 1 ; i <= nums.length; i++){
            if (set.contains(i)) {
                continue;
            } else {
                res.add(i);
            }
        }
        return res;
    }

    //遍历 nums，每遇到一个数 x，就让 nums[x−1] 增加 n。由于 nums 中所有数均在 [1,n] 中，增加以后，这些数必然大于 n。
    //最后我们遍历 nums，若 nums[i] 未大于 n，就说明没有遇到过数 i+1。这样我们就找到了缺失的数字。
    public List<Integer> findDispearedNumbers2(int[] nums){
        int len = nums.length;
        for(int n : nums) {
            int x = (n - 1) % len;
            nums[x] += len;
        }
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < len; i++){
            if (nums[i] <= len) {
                res.add(i+1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 4,5, 6};
        new FindDispearedNumbers().findDispearedNumbers2(nums);
    }
}
