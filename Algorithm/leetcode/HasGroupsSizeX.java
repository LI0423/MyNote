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
