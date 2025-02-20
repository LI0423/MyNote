package Algorithm.leetcode;

import java.util.Arrays;

/**
 * LCS 02. 完成一半题目

有 N 位扣友参加了微软与力扣举办了「以扣会友」线下活动。主办方提供了 2*N 道题目，整型数组 questions 中每个数字对应了每道题目所涉及的知识点类型。
 若每位扣友选择不同的一题，请返回被选的 N 道题目至少包含多少种知识点类型。

示例 1：
输入：questions = [2,1,6,2]
输出：1
解释：有 2 位扣友在 4 道题目中选择 2 题。
 可选择完成知识点类型为 2 的题目时，此时仅一种知识点类型
 因此至少包含 1 种知识点类型。

示例 2：
输入：questions = [1,5,1,3,4,5,2,5,3,3,8,6]
输出：2
解释：有 6 位扣友在 12 道题目中选择题目，需要选择 6 题。
 选择完成知识点类型为 3、5 的题目，因此至少包含 2 种知识点类型。
 */

public class HalfQuestions {

    public static int halfQuestions(int[] questions){
        int[] nums = new int[1001];
        for (int i : questions){
            nums[i]++;
        }
        int count = 0;
        int sum = 0;
        Arrays.sort(nums);
        for(int j = nums.length - 1; j >= 0; j--){
            sum += nums[j];
            count++;
            if (sum >= questions.length / 2){
                break;
            }
        }
        return count;

    }

    public static void main(String[] args) {
        int[] questions = {2,1,6,2};
        System.out.println(halfQuestions(questions));
    }
}
