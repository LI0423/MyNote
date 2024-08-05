package Algorithm.leetcode.greedy;

import java.util.Arrays;

/**
 * LCP 40. 心算挑战

心算项目的挑战比赛中，要求选手从 N 张卡牌中选出 cnt 张卡牌，若这 cnt 张卡牌数字总和为偶数，则选手成绩「有效」且得分为 cnt 张卡牌数字总和。 
给定数组 cards 和 cnt，其中 cards[i] 表示第 i 张卡牌上的数字。 请帮参赛选手计算最大的有效得分。若不存在获取有效得分的卡牌方案，则返回 0。

示例 1：
输入：cards = [1,2,8,9], cnt = 3
输出：18
解释：选择数字为 1、8、9 的这三张卡牌，此时可获得最大的有效得分 1+8+9=18。

示例 2：
输入：cards = [3,3,1], cnt = 1
输出：0
解释：不存在获取有效得分的卡牌方案。
 */

public class MaxmiumScore {

    public static int maxmiumScore(int[] cards, int cnt){
        Arrays.sort(cards);
        int n = cards.length;
        if (n < cnt){
            return 0;
        }
        int sum = 0;
        int minOdd =1001, minEven = 1001;
        for (int i = n - 1; i >= n - cnt; i--){
            int cur = cards[i];
            if ((cur & cards[i]) == 1){
                minOdd = Math.min(minOdd, cur);
            } else {
                minEven = Math.min(minEven, cur);
            }
            sum += cur;
        }
        if ((sum & 1) == 0){
            return sum;
        } else {
            int maxOdd = -1, maxEven = -1;
            for (int i = n - cnt - 1; i >= 0; i--){
                int cur = cards[i];
                if ((cur & cards[i]) == 1){
                    maxOdd = Math.max(maxOdd, cur);
                } else {
                    maxEven = Math.max(maxEven, cur);
                }
            }
            int ans1 = maxEven == -1 ? 0 : minOdd == 1001 ? 0 : sum - minOdd + maxEven;
            int ans2 = maxOdd == -1 ? 0 : minEven == 1001 ? 0 : sum - minEven + maxOdd;
            return Math.max(ans1, ans2);
        }
    }

    public static void main(String[] args) {
        int[] cards = {1,2,8,9};
        int cnt = 3;
        System.out.println(maxmiumScore(cards, cnt));
    }
}
