package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//914. 卡牌分组

// 给定一副牌，每张牌上都写着一个整数。
// 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
// 每组都有 X 张牌。
// 组内所有的牌上都写着相同的整数。
// 仅当你可选的 X >= 2 时返回 true。

// 示例 1：

// 输入：deck = [1,2,3,4,4,3,2,1]
// 输出：true
// 解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
// 示例 2：

// 输入：deck = [1,1,1,2,2,2,3,3]
// 输出：false
// 解释：没有满足要求的分组。

public class HasGroupsSizeX {

    // 暴力算法，首先统计每个数字的个数，然后将数字的个数添加到列表中，接着遍历并增加X的大小，
    // 看原数组的长度是否是X的倍数，如果是的话就遍历列表中的数据，如果列表中的数据不是X的倍数就继续增加X的大小，直到列表中的所有数据遍历完毕。
    public boolean hasGroupsSizeX(int[] deck) {
        int n = deck.length;
        int[] count = new int[10000];
        for (int c : deck) {
            count[c]++;
        }
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 10000; i++){
            if (count[i] > 0) {
                arrayList.add(count[i]);
            }
        }
        for (int X = 2; X <= n; X++){
            if (n % X == 0) {
                boolean flag = true;
                for (int num : arrayList){
                    if (num % X != 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    // 最大公约数，首先统计数组中每个数字的个数，我们只要求出所有数字个数的最大公约数 g，判断 g 是否大于等于 2 即可，如果大于等于 2，则满足条件，否则不满足。
    public boolean hasGroupsSizeX2(int[] deck) {
        int[] count = new int[10000];
        for (int c : deck) {
            count[c]++;
        }
        int g = -1;
        for (int i = 0; i < 10000; i++){
            if (count[i] > 0) {
                if (g == -1) {
                    g = count[i];
                } else {
                    g = gcd(g, count[i]);
                }
            }
        }
        return g >= 2;
    }

    public int gcd(int x, int y) {
        return x == 0 ? y : gcd(y % x, x);
    }
    
}
