package Algorithm.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

//961. 在长度 2N 的数组中找出重复 N 次的元素

// 给你一个整数数组 nums ，该数组具有以下属性：
// nums.length == 2 * n.
// nums 包含 n + 1 个 不同的 元素
// nums 中恰有一个元素重复 n 次
// 找出并返回重复了 n 次的那个元素。

// 示例 1：

// 输入：nums = [1,2,3,3]
// 输出：3
// 示例 2：

// 输入：nums = [2,1,2,5,3,2]
// 输出：2
// 示例 3：

// 输入：nums = [5,1,5,2,5,3,5,4]
// 输出：5

public class RepeatedNTimes {

    // 哈希表，nums中包含n+1个不同的元素，但有一个元素重复n次，则说明其他n个元素都是非重复的，只需遍历找出重复一次的元素返回即可
    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return -1;
    }

    // 数学，我们可以考虑重复的元素 x 在数组 nums 中出现的位置。
    // 如果相邻的 x 之间至少都隔了 2 个位置，那么数组的总长度至少为：
    // n+2(n−1)=3n−2n
    // 当 n>2 时，3n−2>2n，不存在满足要求的数组。因此一定存在两个相邻的 x，它们的位置是连续的，或者只隔了 1 个位置。
    // 当 n=2 时，数组的长度最多为 2n=4，因此最多只能隔 2 个位置。
    // 这样一来，我们只需要遍历所有间隔 2 个位置及以内的下标对，判断对应的元素是否相等即可。
    public int repeatedNTimes2(int[] nums) {
        int n = nums.length;
        for (int gap = 1; gap <= 3; ++gap) {
            for (int i = 0; i + gap < n; ++i) {
                if (nums[i] == nums[i + gap]) {
                    return nums[i];
                }
            }
        }
        return -1;
    }

    // 随机选择，我们可以每次随机选择两个不同的下标，判断它们对应的元素是否相等即可。如果相等，那么返回任意一个作为答案。
    public int repeatedNTimes3(int[] nums) {
        int n = nums.length;
        Random random = new Random();

        while (true) {
            int x = random.nextInt(n), y = random.nextInt(n);
            if (x != y && nums[x] == nums[y]) {
                return nums[x];
            }
        }
    }
    
}
