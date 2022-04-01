package Algorithm.leetcode;

/**
 * 45. 跳跃游戏 II

题目描述
给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
数组中的每个元素代表你在该位置可以跳跃的最大长度。
你的目标是使用最少的跳跃次数到达数组的最后一个位置。
假设你总是可以到达数组的最后一个位置。

示例 1:
输入: nums = [2,3,1,1,4]
输出: 2
解释: 跳到最后一个位置的最小跳跃数是 2。
     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。

示例 2:
输入: nums = [2,3,0,1,4]
输出: 2

解题思路：贪心算法。
    维护当前能够到达的最大下标位置，记为边界。从左到右遍历数组，到达边界时，更新边界将跳跃次数增加1。遍历时不访问最后一个元素，
    在访问最后一个元素之前，边界一定大于等于最后一个位置，否则无法跳到最后一个位置，如果访问最后一个元素，在边界正好为最后一个
    位置的情况下，会增加一次不必要的跳跃次数。
 */

public class JumpGameII {
    public static int jumpGameII(int[] nums){
        int end = 0;
        int mx = 0;
        int steps = 0;
        for(int i = 0;i<nums.length-1;++i){
            mx = Math.max(mx, i+nums[i]);
            if(i == end){
                end = mx;
                ++steps;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,1,4};
        jumpGameII(nums);
    }
}
