package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//989. 数组形式的整数加法

// 整数的 数组形式  num 是按照从左到右的顺序表示其数字的数组。
// 例如，对于 num = 1321 ，数组形式是 [1,3,2,1] 。
// 给定 num ，整数的 数组形式 ，和整数 k ，返回 整数 num + k 的 数组形式 。

// 示例 1：

// 输入：num = [1,2,0,0], k = 34
// 输出：[1,2,3,4]
// 解释：1200 + 34 = 1234
// 示例 2：

// 输入：num = [2,7,4], k = 181
// 输出：[4,5,5]
// 解释：274 + 181 = 455
// 示例 3：

// 输入：num = [2,1,5], k = 806
// 输出：[1,0,2,1]
// 解释：215 + 806 = 1021

public class AddToArrayForm {

    // 逐位相加，从低位到高位依次计算 3+2、2+1 和 1+9。任何时候，若加法的结果大于等于 10，把进位的 1 加入到下一位的计算中，所以结果是1035
    public List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = num.length - 1; i >= 0; i--) {
            int sum = num[i] + k % 10;
            k /= 10;
            if (sum >= 10) {
                k++;
                sum -= 10;
            }
            list.add(sum);
        }
        for (; k > 0; k /= 10) {
            list.add(k % 10);
        }
        Collections.reverse(list);
        return list;
    }

    // 将整个加数 k 加入数组表示的数的最低位。上面的例子 123+912，我们把它表示成 [1,2,3+912]。然后，我们计算 3+912=915。5 留在当前这一位，将 910/10=91 以进位的形式加入下一位。
    public List<Integer> addToArrayForm2(int[] num, int k) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = num.length; i >= 0 || k > 0; i--, k /= 10) {
            if (i >= 0) {
                k += num[i];
            }
            list.add(k % 10);
        }
        Collections.reverse(list);
        return list;
    }
}
