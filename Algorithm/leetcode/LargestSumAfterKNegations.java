package Algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//1005. K 次取反后最大化的数组和

// 给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
// 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
// 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
// 以这种方式修改数组后，返回数组 可能的最大和 。

// 示例 1：

// 输入：nums = [4,2,3], k = 1
// 输出：5
// 解释：选择下标 1 ，nums 变为 [4,-2,3] 。
// 示例 2：

// 输入：nums = [3,-1,0,2], k = 3
// 输出：6
// 解释：选择下标 (1, 2, 2) ，nums 变为 [3,1,0,2] 。
// 示例 3：

// 输入：nums = [2,-3,-1,5,-4], k = 2
// 输出：13
// 解释：选择下标 (1, 4) ，nums 变为 [2,3,-1,5,4] 。

public class LargestSumAfterKNegations {

    public int largestSumAfterKNegations(int[] nums, int k) {
        while (k > 0) {
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < min) {
                    min = nums[i];
                    index = i;
                }
            }
            nums[index] *= -1;
            k--;
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count += nums[i];
        }
        return count;
    }

    //因为将负数 −x 修改成 x 会使得数组的和增加 2x，所以这样的贪心操作是最优的。
    //当给定的 K 小于等于数组中负数的个数时，我们按照上述方法从小到大依次修改每一个负数即可。但如果 K 的值较大，那么我们不得不去修改非负数（即正数或者 0）了。由于修改 0 对数组的和不会有影响，而修改正数会使得数组的和减小，因此：
    //如果数组中存在 0，那么我们可以对它进行多次修改，直到把剩余的修改次数用完；
    //如果数组中不存在 0 并且剩余的修改次数是偶数，由于对同一个数修改两次等价于不进行修改，因此我们也可以在不减小数组的和的前提下，把修改次数用完；
    //如果数组中不存在 0 并且剩余的修改次数是奇数，那么我们必然需要使用单独的一次修改将一个正数变为负数（剩余的修改次数为偶数，就不会减小数组的和）。为了使得数组的和尽可能大，我们就选择那个最小的正数。
    //需要注意的是，在之前将负数修改为正数的过程中，可能出现了（相较于原始数组中最小的正数）更小的正数，这一点不能忽略。
    public int largestSumAfterKNegations2(int[] nums, int k) {
        Map<Integer,Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        int ans = Arrays.stream(nums).sum();
        for (int i = -100; i < 0; ++i) {
            if (freq.containsKey(i)) {
                int ops = Math.min(k, freq.get(i));
                ans += (-i) * ops * 2;
                freq.put(i, freq.get(i) - ops);
                freq.put(-i, freq.getOrDefault(-i, 0) + ops);
                k -= ops;
                if (k == 0) {
                    break;
                }
            }
        }
        if (k ==0 && k % 2 == 1 && !freq.containsKey(0)) {
            for (int i = 0; i < 100; ++i) {
                if (freq.containsKey(i)) {
                    ans -= i * 2;
                    break;
                }
            }
        }
        return ans;
    }
    
}
